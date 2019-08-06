package com.airhacks.business.reminders.boundary;

import com.airhacks.business.reminders.entity.Profile;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

@Stateless
public class ProfileManager {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String path = servletContext.getRealPath("");
        save(new Profile("The Looks","Dennis Reynolds is the co-owner of Paddy's Pub, and Dee Reynolds's twin brother. Dennis is best friends with Charlie and Mac. It is often hinted that Dennis may be a serial killer, but this remains ambiguous. ",path + File.separator + "resources" + File.separator + "images" + File.separator + "dennis.jpg"));
        save(new Profile("Wildcard","Charles \"Charlie\" Kelly is the janitor of Paddy's Pub. He is a member of the Gang that works at the bar, and is considered the \"wild card\" of the group. Charlie is best friends with Dennis and Mac, knowing the latter since childhood. He shares an apartment with Frank Reynolds, who is very likely his biological father.\n" +
                "Charlie is a co-owner of Paddy's Pub, but he sold most of his shares to Dennis and Mac for, among other things, half a sandwich (\"The Gang Sells Out\"). The most tedious and disgusting janitorial tasks at the bar are referred to as Charlie Work, even when Charlie is temporarily spared from performing them.\n" +
                "Charlie is known for his illiteracy, his habit of slipping into a variety of characters for his various schemes, and his never ending obsession with The Waitress (until season 12 episode 10, \"Dennis' Double Life\" when he finds out what's it's like being in a relationship with her.) Although he is by most respects the stupidest member of the Gang, he is capable of rare, impressive moments of brilliance, particularly with music and engineering complex social schemes.\n" +
                "Charlie is portrayed by Charlie Day. ",path + File.separator + "resources" + File.separator + "images" + File.separator + "charlie.jpg"));
        save(new Profile("The Muscle","Franklin \"Frank\" Reynolds (aka: The Warthog) is the father of Dennis Reynolds and Dee Reynolds, and the ex-husband of Barbara Reynolds. Frank also lives with his apparent biological son (and ex-husband), Charlie Kelly.\n" +
                "He is a member of \"The Gang\" that owns and runs Paddy's Pub. He used to be prim and proper, but after he divorced Barbara, he let himself lose all convictions and live with no moral compass or decency. ",path + File.separator + "resources" + File.separator + "images" + File.separator + "frank.png"));
        save(new Profile("The Brains","Ronald \"Mac\" McDonald is a co-owner and the bad bouncer/bodyguard of Paddy's Pub and generally the pub's most active manager. He is roommates with his best friend from high school, Dennis Reynolds, and has been best friends with Charlie Kelly since childhood. Mac is a member of The Gang, considering himself to be the \"brains\" of the operation, even though with those muscles, he is definitely the brawn.  \n" +
                "Mac gets upset at injustices, but will pounce on any opportunity he sees as a fair chance to bang or make money. He was proud of his body and weight gain during season 7, and is secretly ashamed of returning to his regular size.\n" +
                "Mac is portrayed by Rob McElhenney.\n" +
                "In season 12, Mac comes out as gay which isn't a shock to anyone. ",path + File.separator + "resources" + File.separator + "images" + File.separator + "mac.png"));
        save(new Profile("The Useless Chick","Deandra \"Dee\" Reynolds (aka: Sweet Dee) is the bartender at Paddy's Pub. She is the twin sister of Dennis Reynolds, and the legal daughter of Frank and Barbara Reynolds. Dee considers herself a member of The Gang that runs Paddy's, although the rest of the gang often disagrees with this assertion. Dee lived alone in her apartment with her cat (\"Mac and Dennis Break Up\"), but lost it in the wall and when Mac and Dennis apartment burns down (\"The Gang Squashes Their Beefs\"), they have to move in with her and an old black man joins in Season 11 (\"Mac & Dennis Move to the Suburbs[1]). She rarely has success with men and she is an aspiring actress. Her best (and only) friend is Artemis Dubois. In season 6, Dee gave birth to a child using a donor's egg and Carmen's sperm and gave the baby to Carmen and her new husband (\"Dee Gives Birth\"). ",path + File.separator + "resources" + File.separator + "images" + File.separator + "dee.png"));
    }

    public Profile findById(long id) {
        return this.em.find(Profile.class, id);
    }

    public void delete(long id) {
        try{
            Profile reference = this.em.getReference(Profile.class, id);
            this.em.remove(reference);
        } catch(EntityNotFoundException e) {
            //do nothing
        }
    }

    public List<Profile> getAll() {
        return this.em.createNamedQuery(Profile.findAll, Profile.class).getResultList();
    }

    public Profile save(Profile profile) {
        return this.em.merge(profile);
    }

}
