
package utilities;

import java4d.sections.sect;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;



public class sectJTreeBuilder {

    public static void draw(sect root, JTree t) {

        t.setRootVisible(true);
        ((DefaultTreeModel)t.getModel()).setRoot(null);
        t.setModel(new DefaultTreeModel(new myNode("Root")));

        if (root != null) {
            myNode tree = new myNode(root.getTitle());
            ((myNode) t.getModel().getRoot()).add(tree);
            recAdd(root, tree);
            t.expandRow(0);
            t.expandRow(1);
        }

        t.setRootVisible(false);

    }

    private static void recAdd(sect s, myNode n) {
        n.setSect(s);
        n.setUserObject(s.getTitle()+ "  ");

        if (s.getChildren() != null) {
            for (int i = 0; i < s.getChildren().size(); i++) {
                myNode k = new myNode();
                n.add(k);
                recAdd(s.getChildren().get(i), k);
            }

        }
    }
}