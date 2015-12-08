package com.github.roishon.simpleselenium.utils;
/**
 * Enum represents the attribute of an HTML-Tag, upon which the test can distinguish between members of a
 * List, which all refer to GUI-Elements.
 * For example: A menu with a few links originated from following HTML code:
 * <div>
 *     <a class = "one"></a>
 *     <a class = "two></a>
 *     <a class = "tree></a>
 * </div>
 *
 * The class member e.g.:
 *
 * List<Selement> menu
 *
 * Those 3 links are differnce by the value of the attribute class.
 * So for the methods that use to find a specific element from a list (See class SelUtil)
 * for this case the member HTML.CSS will be used as parameter
 *
 * Created by Roi Shachor on 09.07.15.
 */
public enum HTML {

    ID, HREF, NAME, DATA_ITEM_VALUE, DATA_LABEL, TEXT, VALUE, CSS

}
