package com.resume.parser;

import static gate.Utils.stringFor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.util.GateException;
import gate.util.Out;

@Component
public class ResumeParserProgram {
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	

	public File parseToHTMLUsingApacheTikka(String file) throws IOException, SAXException, TikaException {

		String ext = FilenameUtils.getExtension(file);
		String outputFileFormat = "";

		File fis = null;

		if (ext.equalsIgnoreCase("html") | ext.equalsIgnoreCase("pdf") | ext.equalsIgnoreCase("doc")
				| ext.equalsIgnoreCase("docx")) {
			outputFileFormat = ".html";
		} else if (ext.equalsIgnoreCase("txt") | ext.equalsIgnoreCase("rtf")) {
			outputFileFormat = ".txt";
		} else {
			System.out.println("Input format of the file " + file + " is not supported.");
			return null;
		}

		String OUTPUT_FILE_NAME = FilenameUtils.removeExtension(file) + outputFileFormat;
		System.out.println(outputFileFormat);
		ContentHandler handler = new ToXMLContentHandler();

		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();

		try (InputStream stream = new FileInputStream(file);
				FileWriter htmlFileWriter = new FileWriter(OUTPUT_FILE_NAME)) {

			parser.parse(stream, handler, metadata);
			htmlFileWriter.write(handler.toString());

			htmlFileWriter.flush();
			System.out.println(htmlFileWriter);
			fis = new File(OUTPUT_FILE_NAME);
			return fis;
		} finally {
			metadata = null;
			parser = null;
			handler = null;
			if (fis != null) {

				fis.deleteOnExit();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject loadGateAndAnnie(File file) throws GateException, IOException {

		System.setProperty("gate.site.config", System.getProperty("user.dir") + "/GATEFiles/gate.xml");
		if (Gate.getGateHome() == null)
			Gate.setGateHome(new File(System.getProperty("user.dir") + "/GATEFiles"));
		if (Gate.getPluginsHome() == null)
			Gate.setPluginsHome(new File(System.getProperty("user.dir") + "/GATEFiles/plugins"));
		Gate.init();

		Annie annie = new Annie();
		annie.initAnnie();
		URL u = null;
		Corpus corpus = Factory.newCorpus("Annie corpus");
		u = file.toURI().toURL();

		FeatureMap params = Factory.newFeatureMap();
		params.put("sourceUrl", u);
		params.put("preserveOriginalContent", new Boolean(true));
		params.put("collectRepositioningInfo", new Boolean(true));
		Out.prln("Creating doc for " + u);
		Document resume = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
		corpus.add(resume);

		annie.setCorpus(corpus);
		annie.execute();

		Iterator iter = corpus.iterator();
		JSONObject parsedJSON = new JSONObject();
		Out.prln("Started parsing...");
		if (iter.hasNext()) {
			JSONObject profileJSON = new JSONObject();
			Document doc = (Document) iter.next();
			AnnotationSet defaultAnnotSet = doc.getAnnotations();

			AnnotationSet curAnnSet;
			Iterator it;
			Annotation currAnnot;

			// Name
			curAnnSet = defaultAnnotSet.get("NameFinder");
			if (curAnnSet.iterator().hasNext()) {
				currAnnot = (Annotation) curAnnSet.iterator().next();
				String gender = (String) currAnnot.getFeatures().get("gender");
				if (gender != null && gender.length() > 0) {
					profileJSON.put("gender", gender);
				}

				// Needed name Features
				JSONObject nameJson = new JSONObject();
				String[] nameFeatures = new String[] { "firstName", "middleName", "surname" };
				for (String feature : nameFeatures) {
					String s = (String) currAnnot.getFeatures().get(feature);
					if (s != null && s.length() > 0) {
						nameJson.put(feature, s);
					}
				}
				profileJSON.put("name", nameJson);
				nameJson = null;
			}

			curAnnSet = defaultAnnotSet.get("TitleFinder");
			if (curAnnSet.iterator().hasNext()) {
				currAnnot = (Annotation) curAnnSet.iterator().next();
				String title = stringFor(doc, currAnnot);
				if (title != null && title.length() > 0) {
					profileJSON.put("title", title);
				}
			}

			String[] annSections = new String[] { "EmailFinder", "AddressFinder", "PhoneFinder", "URLFinder" };
			String[] annKeys = new String[] { "email", "address", "phone", "url" };
			for (short i = 0; i < annSections.length; i++) {
				String annSection = annSections[i];
				curAnnSet = defaultAnnotSet.get(annSection);
				it = curAnnSet.iterator();
				JSONArray sectionArray = new JSONArray();
				while (it.hasNext()) {
					currAnnot = (Annotation) it.next();
					String s = stringFor(doc, currAnnot);
					if (s != null && s.length() > 0) {
						sectionArray.add(s);
					}
				}
				if (sectionArray.size() > 0) {
					profileJSON.put(annKeys[i], sectionArray);
				}
				sectionArray = null;
			}
			if (!profileJSON.isEmpty()) {
				parsedJSON.put("basics", profileJSON);
			}

			String[] otherSections = new String[] { "summary", "education_and_training", "skills", "accomplishments",
					"awards", "credibility", "extracurricular", "misc" };
			for (String otherSection : otherSections) {
				curAnnSet = defaultAnnotSet.get(otherSection);
				it = curAnnSet.iterator();
				JSONArray subSections = new JSONArray();
				while (it.hasNext()) {
					JSONObject subSection = new JSONObject();
					currAnnot = (Annotation) it.next();
					String key = (String) currAnnot.getFeatures().get("sectionHeading");
					String value = stringFor(doc, currAnnot);
					if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
						subSection.put(key, value);
					}
					if (!subSection.isEmpty()) {
						subSections.add(subSection);
					}
				}
				if (!subSections.isEmpty()) {
					parsedJSON.put(otherSection, subSections);
				}
				subSections = null;
			}

			curAnnSet = defaultAnnotSet.get("work_experience");
			it = curAnnSet.iterator();
			JSONArray workExperiences = new JSONArray();

			while (it.hasNext()) {
				JSONObject workExperience = new JSONObject();
				currAnnot = (Annotation) it.next();
				String key = (String) currAnnot.getFeatures().get("sectionHeading");
				if (key.equals("work_experience_marker")) {

					String[] annotations = new String[] { "date_start", "date_end", "jobtitle", "organization" };
					for (String annotation : annotations) {
						String v = (String) currAnnot.getFeatures().get(annotation);
						if (!StringUtils.isBlank(v)) {
							workExperience.put(annotation, v);
						}
					}
					key = "text";

				}

				String value = stringFor(doc, currAnnot);
				if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
					workExperience.put(key, value);
				}
				if (!workExperience.isEmpty()) {
					workExperiences.add(workExperience);
				}

			}
			if (!workExperiences.isEmpty()) {
				parsedJSON.put("work_experience", workExperiences);
			}
			workExperiences = null;
		}

		String mobileNumber = null;

		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while (line != null) {
				Matcher m = p.matcher(line);
				while (m.find()) {
					mobileNumber = m.group();
					break;
				}
				line = br.readLine();
			}
		} finally {
			if (u != null)
				u = null;
			int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

			System.out.println(pid);
			LocalDateTime now = LocalDateTime.now();

			ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5, taskExecutor);

			newScheduledThreadPool.schedule(() -> {
		
			}, Duration.between(now, now.plusSeconds(10)).toMillis(), TimeUnit.MILLISECONDS);

		}

		if (mobileNumber != null && !mobileNumber.isEmpty())
			parsedJSON.put("mobile", mobileNumber);

		return parsedJSON;

	}

}
