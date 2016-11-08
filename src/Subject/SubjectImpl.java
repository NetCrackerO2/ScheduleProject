package Subject;


import Cathedra.Cathedra;

/**
 * @author AsGreyWolf
 */
public class SubjectImpl implements Subject {
    private Cathedra cathedra;
    private String name;

    protected SubjectImpl(Cathedra cathedra, String name) {
        this.cathedra = cathedra;
        this.name = name;
    }

	@Override
	public Cathedra getCathedra() {
		return cathedra;
	}

	@Override
	public String getName() {
		return name;
	}


}
