package es.sinjava

class Worker {

	Long id
	String name
	String password
	String email
	Date dateCreated


	static constraints = {
		name(blank:false , maxSize:10)
		password(blank:false, maxSize:10)
		email(blank:true)
	}
}
