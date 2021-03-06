/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.RecipeDTO;
import entities.Recipe;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author jelle
 */
public class MadPlanFacade {
    
    private static MadPlanFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MadPlanFacade() {}
    
     private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
     
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
         public static MadPlanFacade getMadPlanFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MadPlanFacade();
        }
        return instance;
    }
     
    public List<RecipeDTO> getAllRecipes(){
        EntityManager em = getEntityManager();
        List<RecipeDTO> RecipeDTOs = new ArrayList<RecipeDTO>();
        TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipes r", Recipe.class);
        List<Recipe> recipes = query.getResultList();
        for (int i = 0; i < recipes.size(); i++) {
            RecipeDTOs.add(new RecipeDTO(recipes.get(i)));
        }
        em.close();
        return RecipeDTOs;
    }
     
    public List<RecipeDTO> getRecipeBySearch(String criteria) {
        EntityManager em = getEntityManager();
        
        List<RecipeDTO> RecipeDTOs = new ArrayList<>();
        TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipes r WHERE r.id = :criteria", Recipe.class)
                .setParameter("criteria", criteria);
        List<Recipe> recipes = query.getResultList();
        for (int i = 0; i < recipes.size(); i++) {
            RecipeDTOs.add(new RecipeDTO(recipes.get(i)));
            
        }
        em.close();
        return RecipeDTOs;
    }
    
}