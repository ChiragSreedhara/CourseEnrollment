package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Abstract user class for children (student and registrar) to extend
 * 
 * @author Chirag Sreedhara
 * @author Ryan Stauffer
 * @author Fahad Ansari
 */
public abstract class User {

	/** Student's first name */
	private String firstName;
	/** Student's last name */
	private String lastName;
	/** Student's id number */
	private String id;
	/** Student's email address */
	private String email;
	/** Student's password */
	private String password;

	/**
	 * User constructor
	 * 
	 * @param firstName of user
	 * @param lastName  of user
	 * @param id        of user
	 * @param email     of user
	 * @param password  of user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Hash code for user
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Checks if 2 user obejex are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the student's first name
	 * 
	 * @return firstName first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the student's last name
	 * 
	 * @return lastName last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the student's email
	 * 
	 * @return email email of the student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the student's email to the given email that it should be set as
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException invalid email if the given email is null or
	 *                                  if it is an empty string
	 */
	public void setEmail(String email) {
		if (email == null || email.length() == 0 || !email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}

		int dotIndex = email.lastIndexOf('.');
		int atIndex = email.indexOf('@');
		if (dotIndex < atIndex) {
			throw new IllegalArgumentException("Invalid email");
		}

		this.email = email;
	}

	/**
	 * Returns the student's password
	 * 
	 * @return password password of the student
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the student's password to the specified password that it should be set
	 * as
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException invalid password if the specified password
	 *                                  if null or is an empty string
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Sets the student's first name to the specified first name
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException invalid first name if the first name is null
	 *                                  or is an empty string
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}

		this.firstName = firstName;
	}

	/**
	 * Sets the student's last name to the specified last name
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException invalid last name if the last name is null
	 *                                  or is an empty string
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the student's id number to the given number
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException invalid id if the id is null or is an empty
	 *                                  string
	 */
	protected void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Returns the student's id number
	 * 
	 * @return id the student's id number
	 */
	public String getId() {
		return id;
	}

}