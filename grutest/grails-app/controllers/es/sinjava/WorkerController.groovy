package es.sinjava

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WorkerController {

	static defaultAction= "create"

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Worker.list(params), model:[workerCount: Worker.count()]
	}

	def show(Worker worker) {
		respond worker
	}

	def create() {
		respond new Worker(params)
	}

	@Transactional
	def save(Worker worker) {
		if (worker == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		if (worker.hasErrors()) {
			transactionStatus.setRollbackOnly()
			respond worker.errors, view:'create'
			return
		}

		worker.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'worker.label', default: 'Worker'),
					worker.name
				])
				redirect worker
			}
			'*' { respond worker, [status: CREATED] }
		}
	}

	def edit(Worker worker) {
		respond worker
	}

	@Transactional
	def update(Worker worker) {
		if (worker == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		if (worker.hasErrors()) {
			transactionStatus.setRollbackOnly()
			respond worker.errors, view:'edit'
			return
		}

		worker.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'worker.label', default: 'Worker'),
					worker.id
				])
				redirect worker
			}
			'*'{ respond worker, [status: OK] }
		}
	}

	@Transactional
	def delete(Worker worker) {

		if (worker == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		worker.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'worker.label', default: 'Worker'),
					worker.id
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'worker.label', default: 'Worker'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
