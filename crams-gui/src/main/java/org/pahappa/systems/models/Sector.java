package org.pahappa.systems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "sectors")
@Inheritance(strategy = InheritanceType.JOINED)
public class Sector extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
   

    /**
     * @return
     */
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

     @Column(name = "description", nullable = true, columnDefinition = "LONGTEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
   
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Sector && (super.getId() != null) ? super.getId().equals(((Sector) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
}
