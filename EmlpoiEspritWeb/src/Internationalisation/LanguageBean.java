package Internationalisation;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean(name = "LanguageBean")
@SessionScoped
public class LanguageBean {


	private String currentLanguage= "messages_FR";
	
	public String setLanguagetoFrench (){
		currentLanguage= "messages_FR";
		return null;	
	}	
	public String setLanguagetoEnglish (){
		currentLanguage= "messages_EN";
		return null;		
	}
	
	
	
	
	
	
	
	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	
	
}
