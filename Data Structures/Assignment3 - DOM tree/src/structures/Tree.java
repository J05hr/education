package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root= null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}





	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {

        //initialize variables;
        Stack<TagNode> tags = new Stack<TagNode>();
        String prevLine = null;
        String curLine = sc.nextLine();
        TagNode curNode = new TagNode(null, null, null);


        //loop through the scanner lines
        while (curLine != null) {

            //for the first line just add the mandatory html tag
            if (prevLine == null) {
                root = new TagNode(curLine.substring(1, curLine.length() - 1), null, null);
                //mandatory <html>
                tags.push(root);

            //else check for tag or text
            } else {

                //if it's a closing tag pop the opening tag out of the stack
                if (curLine.contains("</")) {
                    tags.pop();

                //if it's an opening tag add it as a child or sibling
                } else if (curLine.contains("<") && !curLine.contains("</")) {

                    //if the first child of the last tag is empty, add this tag as a child and push it to the stack as a tag
                    if (tags.peek().firstChild == null) {
                        curNode = new TagNode(curLine.substring(1, curLine.length() - 1), null, null);
                        tags.peek().firstChild = curNode;
                        tags.push(curNode);

                    //if the first child is occupied by a node find an empty sibling to place the current node in and push the tag to the stack
                    } else {
                        curNode = tags.peek().firstChild;

                        //find an empty sibling
                        while (curNode.sibling != null) {
                            curNode = curNode.sibling;
                        }
                        curNode.sibling = new TagNode(curLine.substring(1, curLine.length() - 1), null, null);
                        tags.push(curNode.sibling);
                    }

                // if it's text add it as a child or sibling
                } else {

                    //if the first child of the last tag is empty, add this text as a child
                    if (tags.peek().firstChild == null) {
                        curNode = new TagNode(curLine, null, null);
                        tags.peek().firstChild = curNode;

                    //else find an empty sibling and add the text
                    } else {
                        curNode = tags.peek().firstChild;

                        //find an empty sibling
                        while (curNode.sibling != null) {
                            curNode = curNode.sibling;
                        }
                        curNode.sibling = new TagNode(curLine, null, null);
                    }
                }
            }
            prevLine = curLine;
            if (sc.hasNext()) {
                curLine = sc.nextLine();
            } else {
                curLine = null;
            }
        }
    }






	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
	    //use a recursive helper method for inorder traversal
        repHelper(root, oldTag, newTag);
	}

	//start with a Node and do an inorder traversal replacing matching tags along the way
    private void repHelper(TagNode curNode, String oldTag, String newTag) {

        //base case
        if (curNode == null){
            return;
        }

        //find the old tags and replace
        else if (curNode.tag.equals(oldTag)){
            curNode.tag = newTag;
        }

        //recursive traversal
        repHelper(curNode.firstChild, oldTag, newTag);
        repHelper(curNode.sibling, oldTag, newTag);

    }





	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
        //initialize pointers
        TagNode ptr;
        TagNode ptr2;

        //use the helper to find the table tags first row
        ptr = findTable(root);

        if (ptr == null){
            return;
        }

        //find the row it's asking to bold
        for(int r = 1; r < row; r++){
            ptr = ptr.sibling;
        }

        //jump down to the first column
        ptr2 = ptr.firstChild;

        //loop through columns
        while(ptr2 != null){
            //add the tag and move the text to be a child
            ptr2.firstChild = new TagNode("b", ptr2.firstChild, null);
            //move to the next column
            ptr2 = ptr2.sibling;
        }
	}


    //start with a Node and do an inorder traversal to find the table tag
    private TagNode findTable(TagNode curNode){
        TagNode ptr = null;

	    //base case
        if (curNode == null){
            return ptr;
        }

        //return the first child of the table tag
        else if (curNode.tag.equals("table")){
            return curNode.firstChild;
        }

        //recursive traversal
        ptr = findTable(curNode.firstChild);
        if(ptr == null) {
            ptr = findTable(curNode.sibling);
        }

        return ptr;
    }






	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {

	    //have to run it multiple times if you want to delete multiple tags
        //use a recursive helper method
        while(TagExists(root,tag)){
            removeTagHelp(null, root, tag);
        }
	}

    private boolean TagExists(TagNode curNode, String tag) {
        //base case
        if (curNode == null) {
            return false;
        }
        //match found
        else if (curNode.tag.equals(tag)) {
            return true;

        //return true if a match is found in either recursion
        }else{
            //recursive traversal
            return TagExists(curNode.firstChild, tag) || TagExists(curNode.sibling, tag);
        }
    }

    private void removeTagHelp(TagNode prevNode, TagNode curNode, String tag){
        TagNode temp = null;
        TagNode temp2 = null;

        //base case
        if (curNode == null) {
            return;

        //2nd case, matches
        } else if (curNode.tag.matches(tag)){

            //cat 1
            if(tag.matches("b?p?(em)?")){

                //if it's a child
                if (prevNode.firstChild == curNode) {
                    prevNode.firstChild = prevNode.firstChild.firstChild;
                    temp = curNode.sibling;

                //if it's a sibling
                } else if (prevNode.sibling == curNode) {
                    prevNode.sibling = prevNode.sibling.firstChild;
                    temp = curNode.sibling;
                }

                //jump down
                curNode = curNode.firstChild;

                //jump through the siblings.
                while(curNode.sibling != null){
                    curNode = curNode.sibling;
                }

                //reconnect any previous siblings
                curNode.sibling = temp;


            //cat 2
            } else if (tag.matches("(ol)?(ul)?")){

                //if it's a child
                if (prevNode.firstChild == curNode) {
                    prevNode.firstChild = curNode.firstChild;
                    temp = curNode.sibling;

                //if it's a sibling
                } else if (prevNode.sibling == curNode) {
                    prevNode.sibling = prevNode.sibling.firstChild;
                    temp = curNode.sibling;
                }

                //jump down and change the first li to p
                curNode = curNode.firstChild;
                curNode.tag = "p";

                //keep changing until there are no more siblings.
                while(curNode.sibling != null){
                    curNode = curNode.sibling;
                    curNode.tag = "p";
                }

                //reconnect any previous siblings
                curNode.sibling = temp;
            }

        }else{
            prevNode = curNode;
            //recursive traversal
            removeTagHelp(prevNode, curNode.firstChild, tag);
            removeTagHelp(prevNode, curNode.sibling, tag);
        }
    }





	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
        //use a recursive helper method
	    addTagHelper(null,root,word,tag);
	}


    //start with a Node and do an inorder traversal to find the table tag
    private void addTagHelper(TagNode prevNode, TagNode curNode, String word, String tag) {
        String[] splits;

        //base case
        if (curNode == null) {
            return;

        //2nd case, matches
        } else if (curNode.tag.matches("(.*\\s+)?(?i:"+word+")[.,!?:;]?(\\s+.*)?")){
            //exact word, find the word and add the tag node in before them
            if (curNode.tag.matches("(?i:"+word+")[.,!?:;]?")) {
                prevNode.firstChild = new TagNode(tag, curNode, curNode.sibling);
                curNode.sibling = null;
            }

            //part of a longer string
            else if (curNode.tag.matches("(.*\\s+)?(?i:"+word+")[.,!?:;]?(\\s+.*)?")){

                TagNode temp = curNode.sibling;

                //create splits based on spaces
                splits = curNode.tag.split(" ");

                String pretext = "";
                String posttext = "";
                String tagtext = "";
                int wordIndex = 0;

                //loop through the splits and find the first word to tag
                for(int i = 0; i < splits.length; i++) {
                    if (splits[i].matches("(?i:" + word + ")[.,!?:;]?")) {
                        //text we matched
                        tagtext = splits[i];
                        wordIndex = i;
                        break;
                    }
                }

                //gather the previous splits
                for (int j = 0; j < wordIndex; j++) {
                    pretext += splits[j] + " ";
                }
                //gather trailing splits
                for (int k = wordIndex + 1; k < splits.length; k++) {
                    posttext += " " + splits[k];

                }

                //has a pretext
                if(!pretext.equals("")) {
                    curNode.tag = pretext;
                    curNode.sibling = new TagNode(tag, new TagNode(tagtext, null, null), temp);
                    //if there is a also a posttext add it as a sibling and add its original sibling as a next sibling
                    if (!posttext.equals("")) {
                        curNode.sibling.sibling = new TagNode(posttext, null, temp);
                    }

                //only posttext
                }else {
                curNode.tag = tag;
                curNode.firstChild = new TagNode(tagtext, null, null);
                curNode.sibling = new TagNode(posttext, null, null);
                }



                addTagHelper(prevNode, curNode.sibling.sibling, word, tag);
            }
        }else{
            prevNode = curNode;
            //recursive traversal
            addTagHelper(prevNode, curNode.firstChild, word, tag);
            addTagHelper(prevNode, curNode.sibling, word, tag);
        }
    }






	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}



	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
