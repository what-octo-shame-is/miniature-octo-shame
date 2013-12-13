package controlleurModification;

import java.io.Serializable;  
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;  
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  
  








import org.primefaces.model.DefaultTreeNode;  
import org.primefaces.model.TreeNode;  

import sun.org.mozilla.javascript.internal.ast.ForInLoop;
import tn.esprit.edt.persistance.Groupe;


/*
 * 
 
@ManagedBean
@ViewScoped
public class TreeBean {

    private TreeNode rootNode;

     @PostConstruct
     public void init() {
         Groupe root = new Groupe(); // instead get root object from database 
         rootNode = newNodeWithChildren(root, null);
     }

     /**
      *  recursive function that returns a new node with its children
     public TreeNode newNodeWithChildren(Groupe ttParent, TreeNode parent){
          TreeNode newNode= new DefaultTreeNode(ttParent, parent);
          for (Groupe tt : ttParent.){
               TreeNode newNode2= newNodeWithChildren(tt, newNode);
          }
          return newNode;
     }

     public TreeNode getRootNode() {
         return rootNode;
     }

     public void setRootNode(TreeNode node) {
         rootNode = node;
     }

 }
 */
public class TreeBean implements Serializable {  
	  
    private TreeNode root;  
  
    private TreeNode selectedNode;  
  
    public TreeBean() {  
        root = new DefaultTreeNode("Root", null);  
        TreeNode node0 = new DefaultTreeNode("Node 0", root);  
        TreeNode node1 = new DefaultTreeNode("Node 1", root);  
        TreeNode node2 = new DefaultTreeNode("Node 2", root);  
  
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);  
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);  
  
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);  
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);  
  
        TreeNode node000 = new DefaultTreeNode("Node 0.0.0", node00);  
        TreeNode node001 = new DefaultTreeNode("Node 0.0.1", node00);  
        TreeNode node010 = new DefaultTreeNode("Node 0.1.0", node01);  
  
        TreeNode node100 = new DefaultTreeNode("Node 1.0.0", node10);  
    }  
  
    public TreeNode getRoot() {  
        return root;  
    }  
  
    public TreeNode getSelectedNode() {  
        return selectedNode;  
    }  
  
    public void setSelectedNode(TreeNode selectedNode) {  
        this.selectedNode = selectedNode;  
    }  
      
    public void displaySelectedSingle(ActionEvent event) {  
        if(selectedNode != null) {  
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());  
  
            FacesContext.getCurrentInstance().addMessage(null, message);  
        }  
    }  
}  