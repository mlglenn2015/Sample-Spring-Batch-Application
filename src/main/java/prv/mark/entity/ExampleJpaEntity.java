package prv.mark.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * JPA Entity for the EXAMPLE_ENTITY table.
 *
 * @author mlglenn
 */
@Entity
@Table(name = "EXAMPLE_ENTITY")
public class ExampleJpaEntity implements Serializable {

    private static final long serialVersionUID = -7888525975382502327L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_EXAMPLE_ENTITY_ID", sequenceName = "SEQ_EXAMPLE_ENTITY_ID", initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "SEQ_EXAMPLE_ENTITY_ID", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "ANOTHER_ID_COL", nullable = false)
    private Long anotherIdCol;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXAMPLE_DATE_COL", nullable = false)
    private Date exampleDateCol;

    @Basic
    @Column(name = "EXAMPLE_STRING_COL", nullable = false, length = 10)
    private String exampleStringCol;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnotherIdCol() {
        return anotherIdCol;
    }

    public void setAnotherIdCol(Long anotherIdCol) {
        this.anotherIdCol = anotherIdCol;
    }

    public Date getExampleDateCol() {
        return exampleDateCol;
    }

    public void setExampleDateCol(Date exampleDateCol) {
        this.exampleDateCol = exampleDateCol;
    }

    public String getExampleStringCol() {
        return exampleStringCol;
    }

    public void setExampleStringCol(String exampleStringCol) {
        this.exampleStringCol = exampleStringCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExampleJpaEntity that = (ExampleJpaEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (!getAnotherIdCol().equals(that.getAnotherIdCol())) return false;
        if (!getExampleDateCol().equals(that.getExampleDateCol())) return false;
        return getExampleStringCol().equals(that.getExampleStringCol());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getAnotherIdCol().hashCode();
        result = 31 * result + getExampleDateCol().hashCode();
        result = 31 * result + getExampleStringCol().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExampleJpaEntity{" +
                "id=" + id +
                ", anotherIdCol=" + anotherIdCol +
                ", exampleDateCol=" + exampleDateCol +
                ", exampleStringCol='" + exampleStringCol + '\'' +
                '}';
    }
}
