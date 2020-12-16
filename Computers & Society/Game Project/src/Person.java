import java.util.ArrayList;

public class Person implements Comparable<Person> {

       public static enum Sex {
              MALE,
              FEMALE;
       }

       int personID;
       Sex sex;
       String name;
       int birthDate;
       int deathDate;
       String CityState = "";
       Person father;
       Person mother;
       Person spouse;
       ArrayList<Person> children = null;
       Person Spouse = null;
       String Occupation = "";
       Integer Age = 0;
       double Wealth = 0;
       boolean Alive = false;


       public Person(int personID, String name, Sex sex){
          // Person is born...
          this.personID = personID;
          this.name = name;
          this.birthDate = birthDate;
          children = new ArrayList<Person>();
          Alive = true;
       }

       public Person(int personID, String name, Sex sex, int birthDate){
              this.personID = personID;
              this.name = name;
              this.sex = sex;
              this.birthDate = birthDate;
              children = new ArrayList<Person>();
              Alive = true;
       }

       public int getbirthDate() {
              return birthDate;
       }

       public void setbirthDate(int birthDate) {
              this.birthDate = birthDate;
       }

       public int getdeathDate() {
              return deathDate;
       }

       public void setdeathDate(int deathDate) {
              this.deathDate = deathDate;
       }

       public String getSexName() {
              if (this.getSex() == Sex.MALE) {
                     return "\u2642 " + name;
              } else if (this.getSex() == Sex.FEMALE) {
                     return "\u2640 " + name;
              } else {
                     return name;
              }
       }

       public String getName() {
              return name;
       }

       public void setName(String name) {
              this.name = name;
       }

       public int getpersonID() {
              return personID;
       }

       public void setpersonID(int personID) {
              this.personID = personID;
       }

       public Sex getSex() {
              return sex;
       }

       public void setSex(Sex sex) {
              this.sex = sex;
       }

       public boolean hasChildren(){
              if(children.size() == 0)
                     return false;
              return true;
       }

       public ArrayList<Person> getChildren() {
              return children;
       }

       public void setChildren(Person child) {
              if (!children.contains(child)) {
                     children.add(child);
              }
       }

       public boolean hasFather(){
              if(father == null)
                     return false;
              return true;
       }

       public Person getFather() {
              return father;
       }

       public void setFather(Person father) {
              this.father = father;
       }

       public boolean hasMother(){
              if(mother == null)
                     return false;
              return true;
       }

       public Person getMother() {
              return mother;
       }

       public void setMother(Person mother) {
              this.mother = mother;
       }

       public boolean hasSpouse(){
              if(spouse == null)
                     return false;
              return true;
       }

       public Person getSpouse() {
              return spouse;
       }

       public void setSpouse(Person spouse) {
              this.spouse = spouse;
       }

       public int compareTo(Person p) {
              return personID - p.personID;
       }
}