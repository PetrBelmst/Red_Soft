package com.example.demo.frontend.employee;

import com.example.demo.backend.model.Department;
import com.example.demo.backend.model.Employee;
import com.example.demo.backend.model.HeadOfDepartment;
import com.example.demo.backend.model.Position;
import com.example.demo.backend.repository.EmployeeRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;


@Route("employee")
public class EmployeeMainView extends VerticalLayout {

    private final EmployeeRepo repo;
    private final EmployeeEditor editor;
    final Grid<Employee> grid;
    final TextField filter;
    private final Button addNewButton;

    public EmployeeMainView(EmployeeRepo repo, EmployeeEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Employee.class);
        this.filter = new TextField();
        this.addNewButton = new Button("New Employee", VaadinIcon.PLUS.create());

        setSizeFull();

        HorizontalLayout actions = new HorizontalLayout(filter, addNewButton);
        add(actions, grid, editor);

        grid.setSizeFull();
        grid.removeColumnByKey("department");
        grid.removeColumnByKey("headOfDepartment");
        grid.removeColumnByKey("position");
        grid.setColumns("id", "firstName", "lastName");
        grid.addColumn(employee -> {
            Department department = employee.getDepartment();
            return department == null ? "not defined" : department.getName();
        }).setHeader("Department Name");
        grid.addColumn(employee -> {
            HeadOfDepartment head = employee.getHeadOfDepartment();
            return head == null ? "not defined" : head.getLastName();
        }).setHeader("Boss Name");
        grid.addColumn(employee -> {
            Position position = employee.getPosition();
            return position == null ? "not defined" : position.getPositionName();
        }).setHeader("Position");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        filter.setPlaceholder("Filter by last name");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listEmployees(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.editEmployee(e.getValue()));

        addNewButton.addClickListener(e -> editor.editEmployee(new Employee(null, null, null, null, "", "")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listEmployees(filter.getValue());
        });
        listEmployees(null);
    }

    void listEmployees(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
