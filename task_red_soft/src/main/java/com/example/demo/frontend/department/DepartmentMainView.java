package com.example.demo.frontend.department;

import com.example.demo.backend.model.Department;
import com.example.demo.backend.model.HeadOfDepartment;
import com.example.demo.backend.repository.DepartmentRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route("department")
public class DepartmentMainView extends VerticalLayout {

    private final DepartmentRepo repo;
    private final DepartmentEditor editor;
    final Grid<Department> grid;
    final TextField filter;
    private final Button addNewButton;

    public DepartmentMainView(DepartmentRepo repo, DepartmentEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Department.class);
        this.filter = new TextField();
        this.addNewButton = new Button("New Department", VaadinIcon.PLUS.create());

        setSizeFull();

        HorizontalLayout actions = new HorizontalLayout(filter, addNewButton);
        add(actions, grid, editor);

        grid.setSizeFull();
        grid.removeColumnByKey("employeeList");
        grid.removeColumnByKey("headOfDepartment");
        grid.setColumns("id", "name", "phoneNumber", "email");
        grid.addColumn(department -> {
            HeadOfDepartment head = department.getHeadOfDepartment();
            return head == null ? "not defined" : head.getLastName();
        }).setHeader("Head Of Department");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        filter.setPlaceholder("Filter by department name");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listDep(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.editDep(e.getValue()));

        addNewButton.addClickListener(e -> editor.editDep(new Department(null, null, null, "", "", "")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listDep(filter.getValue());
        });
        listDep(null);
    }

    void listDep(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
        }
    }
}
