package it.cabsb.faces.mb.session;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@SessionScoped
@ManagedBean(name="langMB")
public class LanguageManagedBean implements Serializable {

	private static final long serialVersionUID = -3275262970832227531L;

	@ManagedProperty("#{app['application.lang']}")
	private String languages;

	@ManagedProperty("#{app['default.lang']}")
	private String defaultLang;
	
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	
	private Map<String, Locale> locales;
	private String localeCode;
	
	public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
	
	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
	
	public String getDefaultLang() {
		return defaultLang;
	}

    public Locale getLocale() {
        return locale;
    }

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setDefaultLang(String defaultLang) {
		this.defaultLang = defaultLang;
	}

	public Map<String, Locale> getLocales() {
		return locales;
	}

	public void setLocales(Map<String, Locale> locales) {
		this.locales = locales;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	@PostConstruct
	public void init() {
		String[] langs = languages.split(",");
		locales = new LinkedHashMap<String,Locale>();
		for (String lang : langs) {
			locales.put(lang, new Locale(lang));
		}
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(defaultLang));
	}

}
