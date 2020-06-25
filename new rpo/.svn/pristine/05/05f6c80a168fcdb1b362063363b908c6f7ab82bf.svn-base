package com.ojas.rpo.security.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "menu")
@javax.persistence.Entity
public class Menu implements Entity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = false)
	private String menuName;
	@ManyToMany
	@JoinTable(name = "menupermission", joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "per_id", referencedColumnName = "ID"))
	private List<Permissions> permissions;
	@ManyToMany
	@JoinTable(name = "menufeature", joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "feture_ID", referencedColumnName = "ID"))
	private List<Feature> fetures;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permissions> permissions) {
		this.permissions = permissions;
	}

	public List<Feature> getFetures() {
		return fetures;
	}

	public void setFetures(List<Feature> fetures) {
		this.fetures = fetures;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuName=" + menuName + ", permissions=" + permissions + ", fetures=" + fetures
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fetures == null) ? 0 : fetures.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (fetures == null) {
			if (other.fetures != null)
				return false;
		} else if (!fetures.equals(other.fetures))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		return true;
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
