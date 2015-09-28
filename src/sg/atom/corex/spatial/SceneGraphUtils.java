package sg.atom.corex.spatial;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.common.base.Predicate;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SceneGraphHelper provide various functions in the scenegraph and spatial
 * related in corporated with Spatials. SceneGraphHelper consider a scenegraph
 * is just like a repository of data or "a big fat tree model" for slurping and
 * searching around.
 *
 * <p>
 * <b>Features</b>
 *
 * <ul> <li>It provide various ways to do query (scan) in the scenegraph: by
 * spanning type (no condition), By name, by path, by type, by hash, by a path
 * query, a graph query.</li>
 *
 * <li>It provide high performance way : index/ compress/ partition that
 * scenegraph.</li>
 *
 * <li>It provide custom functions/ obversation/ hook/ data in any spatial. So,
 * monitors and events are possible!</li>
 *
 * <li>It provide specific Lock/ transactional/ authority aware functions over
 * any spatial. </li>
 *
 * </ul>
 * <p>
 * SceneGraphUtils.wrap(Node rootNode) builds a SpatialInfos MVCC concurrent
 * tree upon the rootNode. Any operation over the holded SpatialInfo of the
 * associated Node will result as concurrent operations (delay, defered, lazy,
 * parallel ...) and the dataflow is monitored in the dataflow framework.
 *
 * <p>
 * SceneGraphUtils.$(Node rootNode) provide fluent (JQuery like) upon the tree.
 *
 * <p>
 * CHANGE V.1.0: Changed SceneGraphHelper to SceneGraphUtils to get name
 * convention persist.
 *
 * @author atomix
 */
public class SceneGraphUtils {

    private static Control controlResult = null;
    private static Object dataResult = null;
    private static Spatial result = null;
    private static List<Control> controlResults = new ArrayList<Control>();
    private static List<Spatial> spatialResults = new ArrayList<Spatial>();
    private static List<Object> dataResults = new ArrayList<Object>();

    // Finding things ------------------------------------------------------------
    public static Spatial findSpatialByPath(String path, Node searchNode) {
        String name[] = path.split("/");
        Node currentNode = searchNode;
        Spatial result;
        int i = 0;
        for (String currentName : name) {
            i++;
            result = currentNode.getChild(currentName);
            if (result == null) {
                // this current node don't have a child has the current name!
                return null;
            } else {
                if (i == name.length) {
                    return result;
                } else {
                    if (result instanceof Node) {
                        currentNode = (Node) result;
                    } else {
                        // Path inside a Geometry , WRONG!
                        return null;
                    }
                }
            }

        }
        return null;
    }

    public static Spatial findSpatialByControlTravelUp(Spatial sp, Class<? extends Control> aClass) {
        if (sp.getControl(aClass) == null) {
            if (sp.getParent() != null) {
                return findSpatialByControlTravelUp(sp.getParent(), aClass);
            } else {
                return null;
            }
        } else {
            return sp;
        }
    }

    public static <T extends Control> T findControlTravelUp(Spatial sp, Class<T> aClass) {
        if (sp.getControl(aClass) == null) {
            if (sp.getParent() != null) {
                return findControlTravelUp(sp.getParent(), aClass);
            } else {
                return null;
            }
        } else {
            return sp.getControl(aClass);
        }
    }

    public static <T extends Control> T findControl(Spatial sp, final Class<T> aClass, boolean travelUp) {
        controlResult = null;
        sp.breadthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                if (spatial.getControl(aClass) != null) {
                    controlResult = spatial.getControl(aClass);
                }
            }
        });

        return (T) controlResult;

    }

    public static List<Geometry> findGeometries(Node node, List<Geometry> geoms) {
        for (Iterator<Spatial> it = node.getChildren().iterator(); it.hasNext();) {
            Spatial spatial = it.next();
            if (spatial instanceof Geometry) {
                geoms.add((Geometry) spatial);
            } else if (spatial instanceof Node) {
                findGeometries((Node) spatial, geoms);
            }
        }
        return geoms;
    }

    public static Spatial findSpatialByName(final String name, Node parent, final boolean useReg) {
        parent.breadthFirstTraversal(new SceneGraphVisitor() {
            public void visit(Spatial spatial) {
                if (useReg) {
                    if (spatial.getName().matches(name)) {
                        result = spatial;
                        return;
                    }
                } else {
                    if (spatial.getName().equals(name)) {
                        result = spatial;
                        return;
                    }
                }
            }
        });
        return result;
    }

    public static Geometry findGeoByName(String name, Node parent) {

        for (Spatial sp : parent.getChildren()) {
            if (sp instanceof Geometry) {

                if (sp.getName().equals(name)) {
                    return (Geometry) sp;
                }
            }
        }

        for (Spatial sp : parent.getChildren()) {
            if (sp instanceof Node) {
                Geometry result = findGeoByName(name, (Node) sp);
                if (result != null) {
                    return (Geometry) result;
                }
            }
        }
        return null;
    }

    public static Geometry findFirstGeo(Spatial sp) {
        if (sp instanceof Geometry) {
            return (Geometry) sp;
        } else {
            for (Spatial child : ((Node) sp).getChildren()) {
                if (!(child instanceof Geometry)) {
                    continue; // could be an attachment node, ignore.
                }
                Geometry geom = (Geometry) child;
                return geom;
            }
        }
        return null;
    }

    public static <T extends Control> T findFirstControl(Spatial sp, final Class<T> aClass) {
        controlResult = null;
        sp.breadthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                if (spatial.getControl(aClass) != null) {
                    controlResult = spatial.getControl(aClass);
                }
            }
        });

        return (T) controlResult;

    }

    public static <T extends Spatial> T findFirstBy(Class<T> clazz, Predicate<Spatial>... predicate) {
        return null;
    }

    public static <T extends Spatial> List<T> findBy(Class<T> clazz, Predicate<Spatial>... predicate) {
        return null;
    }

    public static Spatial findFirstBy(Predicate<Spatial>... predicate) {
        return null;
    }

    public static List<Spatial> findBy(Predicate<Spatial>... predicate) {
        return null;
    }

    public static <T extends Spatial> List<T> findByPath(Class<T> clazz, String path) {
        return null;
    }

    public static <T extends Spatial> List<T> findByXPath(Class<T> clazz, String path) {
        return null;
    }

    public static <T extends Spatial> List<T> findByGPath(Class<T> clazz, String path) {
        return null;
    }
    //Structure ----------------------------------------------------------------
    //Relational & Graph query --------------------------------------------------------------
    //Functional-------------------------------------------------------------
    //Positional ---------------------------------------------------------------
    //Data & Compress-----------------------------------------------------------
}
