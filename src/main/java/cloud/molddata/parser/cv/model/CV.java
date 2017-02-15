package cloud.molddata.parser.cv.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 13.09.2016.
 */

@Entity
@Table(name = "cves")
@NamedQueries({
        @NamedQuery(name = "cves.findAll", query = "SELECT cv FROM CV cv"),
        @NamedQuery(name = "cves.findByID", query = "SELECT cv FROM CV cv where id=:idReq")
})
public class CV {
    private long id;
    private String skills;
    private String exp;
    private String lang;
    private String edu;
    private String trainings;
    private String objective;

    private Date date;
    private Users usersByFK;

    public CV(){}
    public CV(String skills,String exp, String lang, String edu, Date date,String trainings, String objective){
        this.skills = skills;
        this.exp = exp;
        this.lang = lang;
        this.date = date;
        this.edu = edu;
        this.trainings=trainings;
        this.objective=objective;
    }
    public CV(String skills,String exp, String lang, String edu, Date date, Contact contact,String trainings, String objective){
        this.skills = skills;
        this.exp = exp;
        this.lang = lang;
        this.edu = edu;
        this.date = date;
        this.contact = contact;
        this.trainings=trainings;
        this.objective=objective;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cv", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "cves", cascade = CascadeType.ALL) //mappedBy = "cv", cascade = CascadeType.ALL
    //@JoinColumn(name = "Contact_id")
    //public Contact getContact() {        return this.contact;    }
    private Contact contact;

    //CV has Cont_ID
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Contact_id")
    public Contact getContact() {
        return this.contact;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_id")
    public Users getUsersByFK() {
        return usersByFK;
    }

    public Date getDate() {
        return this.date;
    }
    @Column(name = "Skills", nullable = false, length = 500)
    public String getSkills() {
        return skills;
    }
    @Column(name = "Expierence", nullable = true, length = 500)
    public String getExp() {
        return exp;
    }
    @Column(name = "Language", nullable = true, length = 500)
    public String getLang() {
        return lang;
    }
    @Column(name = "Education", nullable = true, length = 500)
    public String getEdu() {
        return edu;
    }
    @Column(name="Trainings",nullable = true,length = 500)
    public String getTrainings(){return trainings;}
    @Column(name="Objective",nullable = true,length = 100)
    public String getObjective(){return objective;}
    /*@Column(name = "Contact", nullable = true, length = 50)
    public Contact getContact() {
        return contact;
    }*/

    public void setId(long id) {
        this.id = id;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public void setEdu(String edu) {
        this.edu = edu;
    }
    public void setTrainings(String trainings){this.trainings=trainings;}
    public void setObjective(String objective){this.objective=objective;}
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", unique = true, nullable = false, length = 10)
    public void setDate(Date date) {
        this.date = date;
    }
    public void setUsersByFK(Users usersByFK) {
        this.usersByFK = usersByFK;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
