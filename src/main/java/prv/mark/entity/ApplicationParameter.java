package prv.mark.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * JPA Entity for the APPLICATION_PARAMETER table.
 *
 * @author mlglenn
 */
@Entity
@Table(name = "APPLICATION_PARAMETER")
@NamedQueries({
        @NamedQuery(name = "ApplicationParameter.findEnabledByParameterKey",
                query = "select p from ApplicationParameter p where p.parameterKey = ?1 and p.enabled = ?2")
})
public class ApplicationParameter implements Serializable {

    private static final long serialVersionUID = -6846182196890794399L;

    private Long id;
    private String parameterKey;
    private String parameterValue;
    private Boolean enabled;
    private Date createDate;


    @Id
    @SequenceGenerator(name = "SEQ_APPLICATION_PARAMETER_ID",
                        sequenceName = "SEQ_APPLICATION_PARAMETER_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_PARAMETER_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PARAMETER_KEY", nullable = false, length = 100)
    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    @Basic
    @Column(name = "PARAMETER_VALUE", nullable = false, length = 1024)
    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Column(name = "ENABLED", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    @Convert(converter = BooleanToStringConverter.class)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationParameter that = (ApplicationParameter) o;

        if (!getId().equals(that.getId())) return false;
        if (!getParameterKey().equals(that.getParameterKey())) return false;
        if (!getParameterValue().equals(that.getParameterValue())) return false;
        if (!getEnabled().equals(that.getEnabled())) return false;
        return getCreateDate().equals(that.getCreateDate());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getParameterKey().hashCode();
        result = 31 * result + getParameterValue().hashCode();
        result = 31 * result + getEnabled().hashCode();
        result = 31 * result + getCreateDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationParameter{" +
                "id=" + id +
                ", parameterKey='" + parameterKey + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                ", enabled=" + enabled +
                ", createDate=" + createDate +
                '}';
    }
}
