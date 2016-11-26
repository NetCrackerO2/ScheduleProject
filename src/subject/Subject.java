package subject;


import cathedra.Cathedra;


/**
 * @author AsGreyWolf
 */
public interface Subject {
    Cathedra getCathedra();

    void setCathedra(Cathedra cathedra);

    String getName();

    void setName(String name);
}
