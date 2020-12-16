//initialize variables;
        String prevLine = null;
        String curLine = sc.nextLine();
        TagNode prevTag = new TagNode(null, null, null);
        TagNode ptr = new TagNode(null, null, null);
        int prevTagLvl = 0;
        int ptrTagLvl = 0;
        int curTagLvl = 0;


        //loop through the scanner lines
        while (curLine != null) {


            //for the first line just add the mandatory html tag
            if (prevLine == null) {
                root = new TagNode(curLine.substring(1,curLine.length()-1), null, null);
                //mandatory <html>
                ptr = root;


            //else check for tag or text
            } else {


                //if it's an opening tag
                if (curLine.contains("<") && !curLine.contains("</")) {
                    //if the prev line was an opening tag add it
                    if (prevLine.contains("<") && !prevLine.contains("</")) {
                        //lower level gets nested
                        if (curTagLvl < ptrTagLvl || curTagLvl == 1) {
                            ptr.firstChild = new TagNode(curLine.substring(1,curLine.length()-1), null, null);
                            prevTag = ptr;
                            ptr = ptr.firstChild;
                        //equal level gets a sibling
                        } else if (curTagLvl == ptrTagLvl) {
                            ptr.sibling = new TagNode(curLine.substring(1,curLine.length()-1), null, null);
                            prevTag = ptr;
                            ptr = ptr.sibling;
                        //higher level we need to go back and find a > or = tag
                        } else if (curTagLvl > ptrTagLvl) {

                        }

                    //else prev text
                    } else {
                        //if the level is lower then prevtag we can add it as a sibling of the text
                        if (curTagLvl < prevTagLvl) {
                            ptr.sibling = new TagNode(curLine.substring(1,curLine.length()-1), null, null);
                            ptr = ptr.sibling;

                        //if the levels are equal we want to add it as a sibling of the last tag
                        } else if (curTagLvl == prevTagLvl) {
                            prevTag.sibling = new TagNode(curLine.substring(1,curLine.length()-1), null, null);
                            ptr = prevTag.sibling;
                            //if the prevTag is the lower level then we we need to go back and find a > or = tag
                        } else {

                        }
                    }


                    //if it's text
                    }else if (!curLine.contains("</") && !curLine.contains("<")) {
                        //if the prev line was a closing tag add line as a sibling to the prevPtr
                        if (prevLine.contains("</")) {
                            prevTag.sibling = new TagNode(curLine, null, null);

                        //if the prev line was an opening tag add it as a child
                        } else if (prevLine.contains("<")) {
                            ptr.firstChild = new TagNode(curLine, null, null);
                            prevTag = ptr;
                            ptr = ptr.firstChild;

                        //else add the tag as sibling of the prev text.
                        } else {
                            ptr.sibling = new TagNode(curLine, null, null);
                            prevTag = ptr;
                            ptr = ptr.sibling;
                        }
                    }

                }


                //set ptrlevel for the next pass
                if (ptr.tag != null) {
                    if (ptr.tag.matches("html")) {
                        ptrTagLvl = 7;
                    } else if (ptr.tag.matches("body")) {
                        ptrTagLvl = 6;
                    } else if (ptr.tag.matches("p") || ptr.tag.matches("table")) {
                        ptrTagLvl = 5;
                    }else if (ptr.tag.matches("tr") || ptr.tag.matches("td")) {
                        ptrTagLvl = 4;
                    }else if (ptr.tag.matches("ol") || ptr.tag.matches("il")) {
                        ptrTagLvl = 3;
                    }else if (ptr.tag.matches("em") || ptr.tag.matches("b")) {
                        ptrTagLvl = 2;
                    } else {
                        ptrTagLvl = 1;
                    }
                }

                //set prevlevel for the next pass
                if (prevTag.tag != null) {
                    if (prevTag.tag.matches("html")) {
                        prevTagLvl = 7;
                    } else if (prevTag.tag.matches("body")) {
                        prevTagLvl = 6;
                    } else if (prevTag.tag.matches("p") || prevTag.tag.matches("table")) {
                        prevTagLvl = 5;
                    }else if (prevTag.tag.matches("tr") || prevTag.tag.matches("td")) {
                        prevTagLvl = 4;
                    }else if (prevTag.tag.matches("ol") || prevTag.tag.matches("il")) {
                        prevTagLvl = 3;
                    }else if (prevTag.tag.matches("em") || prevTag.tag.matches("b")) {
                        prevTagLvl = 2;
                    } else {
                        prevTagLvl = 1;
                    }
                }


                prevLine = curLine;
                if (sc.hasNext()) {
                    curLine = sc.nextLine();

                    //set the curlevel for the next pass
                    if (curLine.contains("<")) {
                        if (curLine.matches("<html>")) {
                            curTagLvl = 7;
                        } else if (curLine.matches("<body>")) {
                            curTagLvl = 6;
                        } else if (curLine.matches("<p>") || curLine.matches("<table>")) {
                            curTagLvl = 5;
                        }else if (curLine.matches("<tr>") || curLine.matches("<td>")) {
                            curTagLvl = 4;
                        }else if (curLine.matches("<ol>") || curLine.matches("<il>")) {
                            curTagLvl = 3;
                        }else if (curLine.matches("<em>") || curLine.matches("<b>")) {
                            curTagLvl = 2;
                        } else {
                            curTagLvl = 1;
                        }
                    }
                } else {
                    curLine = null;
                }
            }
        }