package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.elements.guiElements.CheckBox;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Represents the PrimeFace Tree component. Contains method to click on checkbox, toggle, text.
 * By all methods  of this class, "current root" is referring to the upper branch.
 * If a method returns a part of the tree, starting from a specific tree branch, This branch
 * is the new root of the returned tree object.
 * Created by Roi Shachor on 09.06.15.
 */
public class PfTreeImpl extends SubPageImpl implements PfTree {


    /**
     * List of Selements, each representing a child Node of the current root of the tree
     */
    @FindBy(xpath = "child::ul/child::li[@role = 'treeitem']")
    List<Selement> childrenNodeRoots;


    /**
     * Represents the checkBox attached to the current root of the tree
     */
    @FindBy(xpath = "child::span/child::div[@class = 'ui-chkbox ui-widget']/child::div")
    CheckBox checkBox;


    /**
     * Represents the label of the current tree node, which contains the text displayed on the creen
     */
    @FindBy(xpath = "child::span/child::span[contains(@class, 'ui-treenode-label')]")
    Selement textSelement;


    /**
     * Represents the toggle button of the current tree node, which is used to clap down or close
     * the next node level
     */
    @FindBy(xpath = "child::span/child::span[contains(@class, 'ui-tree-toggler')]")
    Selement toogler;

    private PfTreeImpl subTree;


    /**
     * Initiate the attribute root, which refers to the HTML-Tag framing this whole SubPage on the GUI
     *
     * @param root  - WebElement which wraps the whole Tree
     */
    public PfTreeImpl(WebElement element) {
        super(element);

    }



    /**
     * Returns a Tree Objects representing a children node of the current node, upon the given name in the parameter.
     * The branch must be a direct child of the current tree root
     * @param name - the text or part of it, of the branch that will be clicked
     * @return - An Tree Object, which root is the clicked branch.
     */
    @Override
    public PfTreeImpl getChildByName(String name) {
        for(Selement el : childrenNodeRoots)
            if(el.getText().contains(name))
                return new PfTreeImpl(el).initElements();

        throw new RuntimeException("");
    }


    /**
     * Returns a Tree Objects representing a children node of the current node,
     * upon a list of names the given name in the parameter. Each name represents a lower level of the Tree.
     * For each name of the list a toggle will be clicked, and the relevant level will be opened, except for the last name
     * which represents the Tree Object to be returned
     * @param names - list of the names upon which the branches will be clicked
     * @return A Tree object, which root is the wanted branch
     */
    public PfTree getChildByName(List<String> names) {

        PfTree subTree = null;

        for(int i = 0; i < names.size(); i++) {
            subTree = getChildByName(names.get(i));

            if(i < (names.size() - 1) && !subTree.isToggleSelected())
                subTree.clickToogler();
        }

        return subTree;
    }


    /**
     * Clicks a child brunch of the current root, based on the name given in the parameter.
     * Waits for the tree to render
     * @param name
     */
    @Override
    public void clickName(String name) {
        click(getChildByName(name).getTextSelement());
        refreshPage();
    }


    /**
     * Clicks several branches based on the names given as list in the parameter.
     * Each name in the list represents a deeper level in the tree hierarchy
     * @param names
     */
    public void clickName(List<String> names) {
        getChildByName(names).getTextSelement().click();
    }



    /**
     * Checks if the current root is selected, meaning, if the children levels are visible
     * @return true, is toggle is clicked
     */
    public boolean isToggleSelected() {
        return toogler.getAttribute("class").contains("1-s");
    }


    /**
     * Clicks toggle button and return this Tree object
     * @return This Tree object
     */
    public PfTree clickToogler() {
        toogler.click();
        return this;
    }


    /**
     * Returns the displayed text of the current root branch
     * @return the text displyed
     */
    public String getBranchText() {
        return textSelement.getText();
    }


    /**
     * Returns a Selement containing the displayed text of the current root branch
     * @return Selement with the text of the current root node
     */
    public Selement getTextSelement() {
        return textSelement;
    }


    /**
     * Clicks the checkbox of the current root
     * @return this Tree object
     */
    public PfTree clickCheckbox() {
        checkBox.toggle();
        return this;
    }


}

