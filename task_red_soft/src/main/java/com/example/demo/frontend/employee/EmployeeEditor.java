package com.example.demo.frontend.employee;

import com.example.demo.backend.model.Employee;
import com.example.demo.backend.repository.EmployeeRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

    private final EmployeeRepo repository;
    private Employee employee;

    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Employee> binder = new Binder<>(Employee.class);
    private ChangeHandler changeHandler;

    @Autowired
    public EmployeeEditor(EmployeeRepo repository) {
        this.repository = repository;
        add(firstName, lastName, actions);
        binder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    void delete() {
        repository.delete(employee);
        changeHandler.onChange();
    }

    void save() {
        repository.save(employee);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editEmployee(Employee emp) {
        if (emp == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = emp.getId() != null;
        if (persisted) {
            emp = repository.findById(emp.getId()).get();
        }
        else {
            employee = emp;
        }
        cancel.setVisible(persisted);
        binder.setBean(employee);
        setVisible(true);
        lastName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
