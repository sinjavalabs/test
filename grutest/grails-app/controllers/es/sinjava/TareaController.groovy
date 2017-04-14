package es.sinjava

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TareaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tarea.list(params), model:[tareaCount: Tarea.count()]
    }

    def show(Tarea tarea) {
        respond tarea
    }

    def create() {
        respond new Tarea(params)
    }

    @Transactional
    def save(Tarea tarea) {
        if (tarea == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tarea.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tarea.errors, view:'create'
            return
        }

        tarea.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tarea.label', default: 'Tarea'), tarea.id])
                redirect tarea
            }
            '*' { respond tarea, [status: CREATED] }
        }
    }

    def edit(Tarea tarea) {
        respond tarea
    }

    @Transactional
    def update(Tarea tarea) {
        if (tarea == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tarea.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tarea.errors, view:'edit'
            return
        }

        tarea.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tarea.label', default: 'Tarea'), tarea.id])
                redirect tarea
            }
            '*'{ respond tarea, [status: OK] }
        }
    }

    @Transactional
    def delete(Tarea tarea) {

        if (tarea == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tarea.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tarea.label', default: 'Tarea'), tarea.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
