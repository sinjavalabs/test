package es.sinjava

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Worker)
class WorkerSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test de creación de trabajadores"() {

		given: " Un nuevo usuario"

		def nuevo= new Worker(name:'Andrés', password:'secreto', email:'no@vale.es')

		when: "Al guardarlo"

		nuevo.save()

		then: " Si todo ha ido bien el id del trabajador es el que se recupera"

		nuevo.errors.errorCount==0

		nuevo.id!=null

		Worker.get(nuevo.id).id == nuevo.id
	}
}
