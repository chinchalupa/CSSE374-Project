package problem.asm;

import org.scijava.swing.checkboxtree.CheckBoxNodeData;
import org.scijava.swing.checkboxtree.CheckBoxNodeEditor;
import org.scijava.swing.checkboxtree.CheckBoxNodeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * Created by Jeremy on 2/16/2016.
 */
public class CheckboxTreeFactory implements ExecuteCapable {

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Class Nodes");
    DefaultTreeModel model;
    private JTree tree;

    private FileGenerator fileGenerator;

    public CheckboxTreeFactory(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }


    public void addTreeNode() {

    }

    @Override
    public void execute() {
        for(INode node : this.fileGenerator.updateNodes()) {
//            System.out.println("...");
            String listIdentifiers = "";
            for (String identifier : node.getPatternIdentifier()) {
                listIdentifiers += identifier;
            }
            listIdentifiers = listIdentifiers.replaceAll("[\\\\<>]", "");
            System.out.println("List Identifier:" + listIdentifiers);

            if(listIdentifiers.equals("")) {
                listIdentifiers = "No Pattern";
            }

            int nodeCount = root.getChildCount();
            boolean hasNode = false;

            DefaultMutableTreeNode sectionNode = null;

            for(int i = 0; i < nodeCount; i++) {
                DefaultMutableTreeNode subNode = (DefaultMutableTreeNode) root.getChildAt(i);

                String userObject = subNode.getUserObject().toString();
                String simpleObject = userObject.substring(userObject.indexOf("[") + 1, userObject.indexOf("/"));

//                System.out.println(listIdentifiers);
                if(simpleObject.equals(listIdentifiers)) {
                    hasNode = true;
                    sectionNode = subNode;
                }
            }


            if(sectionNode == null) {
                CheckBoxNodeData data = new CheckBoxNodeData(listIdentifiers, true);
                root.add(sectionNode = new DefaultMutableTreeNode(data));
            }
            CheckBoxNodeData data = new CheckBoxNodeData(node.getName(), true);
            sectionNode.add(new DefaultMutableTreeNode(data));


        }

        this.model = new DefaultTreeModel(root);
        this.tree = new JTree(this.model);

        tree.setCellRenderer(new CheckBoxNodeRenderer());
        tree.setCellEditor(new CheckBoxNodeEditor(getTree()));
        tree.setEditable(true);

    }

    @Override
    public String getExecutionString() {
        return "Generating Checkboxes";
    }

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }
}
