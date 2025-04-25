package main.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Student extends User {
	
    private ArrayList<Course> coursesTaken;
    private ArrayList<StudentGradebook> gradebooks;
    
    public Student(String username, String password, String firstName, String lastName, String email, boolean isHashed) {
        super(username, password, firstName, lastName, email, isHashed);
		coursesTaken = new ArrayList<Course>();
		gradebooks = new ArrayList<StudentGradebook>();
    }

	//copy constructor
    public Student(Student other) {
        super(other.username, other.password, other.firstName, other.lastName, other.email,true);
    }

	public ArrayList<Assignment> getAllAssignments() {
		ArrayList<Assignment> studentAssignments = new ArrayList<Assignment>();
		for (StudentGradebook gradebook : gradebooks) {
			studentAssignments.addAll(gradebook.getAssignments());
		}
		return studentAssignments;
	}

	public StudentGradebook getGradebookForCourse(Course course) {
		return gradebooks.get(coursesTaken.indexOf(course));
	}

	public void addCourse(Course course) {
		System.out.println("Added course " + course.getName() + " to student " + this.firstName + " " + this.lastName);
		coursesTaken.add(course);
		gradebooks.add(new StudentGradebook(course.getName()));
	}


	public static Comparator<Student> sortByFirstName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.firstName.compareToIgnoreCase(s2.firstName);
				return nameCompare;
			}
		};
	}

	public static Comparator<Student> sortByLastName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.lastName.compareToIgnoreCase(s2.lastName);
				return nameCompare;
			}
		};
	}
	
	public static Comparator<Student> sortByUsername() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.username.compareToIgnoreCase(s2.username);
				return nameCompare;
				}
		};
	}

	public static Comparator<Student> sortByGradeOnAssignment(String assignmentName) {
		return new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				double grade1 = s1.getAssignmentByName(assignmentName).getGrade();
				double grade2 = s2.getAssignmentByName(assignmentName).getGrade();
				return Double.compare(grade1, grade2); 
			}
		};
	}
	

	public Assignment getAssignmentByName(String assignmentName) {
		for (Assignment assignment : getAllAssignments()) {
			if (assignment.getTitle().equals(assignmentName)) {
				return assignment;
			}
		}
		return null;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "Student " + this.firstName + " " + this.lastName + " with username " + username;
	}
    
	public ArrayList<Course> getCoursesForDay(Days day) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (Course course : this.coursesTaken) {
            if (course.getDays().contains(day)) {
                courses.add(course);
            }
        }
        return courses;
        
    }

	public ArrayList<Course> getCourses() {
		return coursesTaken;
	}
}