package resolution;

import java.util.Set;

import tn.esprit.edt.gestion.Services;
import tn.esprit.edt.gestion.ServicesRemote;

public class ResolutionForm {
	 /** Énumération des différents types de messages. */
    public enum TypeMessage {INFO, SUCCES, ERREUR, AVERTISSEMENT}
    private TypeMessage typeMessage;

    private String titreMessages;

    private Set<String> messages;

    private boolean resolu;
   
    private ServicesRemote services;
    

    /**
     *
     *
     */
    
    
    public ResolutionForm (ServicesRemote services) {
        super();
      
        this.services = services;
    }

    public ResolutionForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isResolu() {
        return resolu;
    }

    public void setResolu(boolean resolu) {
        this.resolu = resolu;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }

    public String getTitreMessages() {
        return titreMessages;
    }

    public void setTitreMessages(String titreMessages) {
        this.titreMessages = titreMessages;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    /**
     * Retourne la classe (attribut HTML utilisé par les CSS) du type
     * de message sélectionné.
     * @return
     */
    public String getClassCSSTypeMessage() {
        TypeMessage type = getTypeMessage();
        if(type == null)
            return "";
        switch(type) {
            case SUCCES:          return "succes";
            case INFO:            return "info";
            case AVERTISSEMENT:   return "avertissement";
            case ERREUR: default: return "erreur";
        }
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

}
