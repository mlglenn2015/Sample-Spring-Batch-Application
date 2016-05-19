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
 * JPA Entity for the APPLICATION_MESSAGE table.
 *
 * @author mlglenn
 */
@Entity
@Table(name = "APPLICATION_MESSAGE")
@NamedQueries({
        @NamedQuery(name = "ApplicationMessage.findEnabledByMessageKey",
                query = "select m from ApplicationMessage m where m.messageKey = ?1 and m.enabled = ?2")
})
public class ApplicationMessage implements Serializable {

    private static final long serialVersionUID = 8871947623101334276L;

    private Long id;
    private String messageKey;
    private String messageValue;
    private Boolean enabled;
    private Date createDate;

    @Id
    @SequenceGenerator(name = "SEQ_APPLICATION_MESSAGE_ID",
            sequenceName = "SEQ_APPLICATION_MESSAGE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_MESSAGE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MESSAGE_KEY", nullable = false, length = 100)
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    @Basic
    @Column(name = "MESSAGE_VALUE", nullable = false, length = 1024)
    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
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
    @Column(name = "CREATE_DATE", nullable = false)
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

        ApplicationMessage that = (ApplicationMessage) o;

        if (!getId().equals(that.getId())) return false;
        if (!getMessageKey().equals(that.getMessageKey())) return false;
        if (!getMessageValue().equals(that.getMessageValue())) return false;
        if (!getEnabled().equals(that.getEnabled())) return false;
        return getCreateDate().equals(that.getCreateDate());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getMessageKey().hashCode();
        result = 31 * result + getMessageValue().hashCode();
        result = 31 * result + getEnabled().hashCode();
        result = 31 * result + getCreateDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationMessage{" +
                "id=" + id +
                ", messageKey='" + messageKey + '\'' +
                ", messageValue='" + messageValue + '\'' +
                ", enabled=" + enabled +
                ", createDate=" + createDate +
                '}';
    }
}
