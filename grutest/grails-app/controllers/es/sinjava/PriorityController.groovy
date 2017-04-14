package es.sinjava

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PriorityController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Priority.list(params), model:[priorityCount: Priority.count()]
    }

    def show(Priority priority) {
        respond priority
    }

    def create() {
        respond new Priority(params)
    }

    @Transactional
    def save(Priority priority) {
        if (priority == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (priority.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond priority.errors, view:'create'
            return
        }

        priority.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'priority.label', default: 'Priority'), priority.id])
                redirect priority
            }
            '*' { respond priority, [status: CREATED] }
        }
    }

    def edit(Priority priority) {
        respond priority
    }

    @Transactional
    def update(Priority priority) {
        if (priority == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (priority.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond priority.errors, view:'edit'
            return
        }

        priority.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'priority.label', default: 'Priority'), priority.id])
                redirect priority
            }
            '*'{ respond priority, [status: OK] }
        }
    }

    @Transactional
    def delete(Priority priority) {

        if (priority == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        priority.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'priority.label', default: 'Priority'), priority.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'priority.label', default: 'Priority'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
