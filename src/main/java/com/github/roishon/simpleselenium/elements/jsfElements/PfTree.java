package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import org.openqa.selenium.support.FindBy;
import java.util.List;


/**
 * Represents the PrimeFace Tree component. Contains method to click on checkbox, toggle, text.
 * By all methods  of this class, "current root" is referring to the upper branch.
 * If a method returns a part of the tree, starting from a specific tree branch, This branch
 * is the new root of the returned tree object.
 * Created by Roi Shachor on 09.06.15.
 */
@FindBy(css =".ui-tree-container > li[role='treeitem']")
@ImplementingClass(PfTreeImpl.class)
public interface PfTree extends SubPage {



    /**
     * Returns a Tree Objects representing a children node of the current node, upon the given name in the parameter.
     * The branch must be a direct child of the current tree root
     * @param name - the text or part of it, of the branch that will be clicked
     * @return - An Tree Object, which root is the clicked branch.
     */
    public PfTree getChildByName(String name);


    /**
     * Returns a Tree Objects representing a children node of the current node,
     * upon a list of names the given name in the parameter. Each name represents a lower level of the Tree.
     * For each name of the list a toggle will be clicked, and the relevant level will be opened, except for the last name
     * which represents the Tree Object to be returned
     * @param names - list of the names upon which the branches will be clicked
     * @return A Tree object, which root is the wanted branch
     */
    public PfTree getChildByName(List<String> names);

    /**
     * Clicks toggle button and return this Tree object
     * @return This Tree object
     */
    public PfTree clickToogler();


    /**
     * Returns the displayed text of the current root branch
     * @return the text displyed
     */
    public String getBranchText();


    /**
     * Returns a Selement containing the displayed text of the current root branch
     * @return Selement with the text of the current root node
     */
    public Selement getTextSelement();


    /**
     * Clicks the checkbox of the current root
     * @return this Tree object
     */
    public PfTree clickCheckbox();


    /**
     * Clicks several branches based on the names given as list in the parameter.
     * Each name in the list represents a deeper level in the tree hierarchy
     * @param names
     */
    public void clickName(List<String> names);


    /**
     * Clicks a child brunch of the current root, based on the name given in the parameter.
     * Waits for the tree to render
     * @param name
     */
    public void clickName(String name);


    /**
     * Checks if the current root is selected, meaning, if the children levels are visible
     * @return true, is toggle is clicked
     */
    public boolean isToggleSelected();



}

