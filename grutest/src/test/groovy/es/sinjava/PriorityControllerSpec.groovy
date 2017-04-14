package es.sinjava

import grails.test.mixin.*
import spock.lang.*

@TestFor(PriorityController)
@Mock(Priority)
class PriorityControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.priorityList
            model.priorityCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.priority!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def priority = new Priority()
            priority.validate()
            controller.save(priority)

        then:"The create view is rendered again with the correct model"
            model.priority!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            priority = new Priority(params)

            controller.save(priority)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/priority/show/1'
            controller.flash.message != null
            Priority.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def priority = new Priority(params)
            controller.show(priority)

        then:"A model is populated containing the domain instance"
            model.priority == priority
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def priority = new Priority(params)
            controller.edit(priority)

        then:"A model is populated containing the domain instance"
            model.priority == priority
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/priority/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def priority = new Priority()
            priority.validate()
            controller.update(priority)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.priority == priority

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            priority = new Priority(params).save(flush: true)
            controller.update(priority)

        then:"A redirect is issued to the show action"
            priority != null
            response.redirectedUrl == "/priority/show/$priority.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/priority/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def priority = new Priority(params).save(flush: true)

        then:"It exists"
            Priority.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(priority)

        then:"The instance is deleted"
            Priority.count() == 0
            response.redirectedUrl == '/priority/index'
            flash.message != null
    }
}
