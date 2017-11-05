package domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="tst_case")
public class TCase {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, 
			targetEntity=TStep.class, 
			fetch=FetchType.EAGER, 
			mappedBy="pk.tcid")
	private List<TStep> steps;

	public TCase() {

	}
	public TCase(String name) {
		this.name = name;
	}
	public TCase(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TStep> getSteps() {
		return steps;
	}

	public void setSteps(List<TStep> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "TCase [id=" + id + ", name=" + name + ", steps=" + steps + "]";
	}
}
