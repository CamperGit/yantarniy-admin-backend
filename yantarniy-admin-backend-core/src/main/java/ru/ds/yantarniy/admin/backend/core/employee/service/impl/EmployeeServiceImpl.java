package ru.ds.yantarniy.admin.backend.core.employee.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeCreateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.model.EmployeeUpdateRequest;
import ru.ds.yantarniy.admin.backend.core.employee.model.SearchEmployeesModel;
import ru.ds.yantarniy.admin.backend.core.employee.service.EmployeeService;
import ru.ds.yantarniy.admin.backend.core.file.model.FileUploadRequest;
import ru.ds.yantarniy.admin.backend.core.file.service.FileService;
import ru.ds.yantarniy.admin.backend.core.search.SpecificationsSearchService;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeEntity;
import ru.ds.yantarniy.admin.backend.dao.entity.employee.EmployeeRepository;
import ru.ds.yantarniy.admin.backend.dao.entity.file.FileEntity;
import ru.ds.yantarniy.admin.backend.dao.specification.Specifications;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.ds.yantarniy.admin.backend.dao.specification.Specifications.equalOrReturnNull;
import static ru.ds.yantarniy.admin.backend.dao.specification.Specifications.likeOrReturnNull;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

    static String ID_PROPERTY_NAME = "id";
    EmployeeRepository employeeRepository;

    FileService fileService;

    @Override
    public EmployeeEntity create(EmployeeCreateRequest request) {
        EmployeeEntity entity = request.getEntity();
        Optional.ofNullable(request.getFileUploadRequest()).ifPresent(fileUploadRequest -> {
            FileEntity file = fileService.upload(fileUploadRequest);
            entity.setFile(file);
        });
        return save(entity);
    }

    @Override
    public EmployeeEntity update(EmployeeUpdateRequest request) {
        EmployeeEntity entity = request.getEntity();
        Optional<FileUploadRequest> fileUploadRequest = Optional.ofNullable(request.getFileUploadRequest());
        if (fileUploadRequest.isPresent()) {
            FileEntity newFile = fileService.upload(fileUploadRequest.get());
            FileEntity oldFile = entity.getFile();
            entity.setFile(newFile);
            entity = save(entity);
            Optional.ofNullable(oldFile).ifPresent(currentEntityFile -> fileService.deleteById(currentEntityFile.getId()));
            return entity;
        } else {
            return save(entity);
        }
    }

    @Override
    public EmployeeEntity save(EmployeeEntity entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found EmployeeEntity with id = %d", id)));
    }

    @Override
    public Page<EmployeeEntity> search(SearchEmployeesModel searchModel) {
        int pageNumber = searchModel.getPageNumber();
        int pageSize = searchModel.getPageSize();
        Sort.Direction sortDirection = searchModel.getSortDirection();
        String sortProperty = searchModel.getSortProperty();
        List<Specification<EmployeeEntity>> specifications = Arrays.asList(
                equalOrReturnNull("type.id", searchModel.getTypeId()),
                equalOrReturnNull("location.id", searchModel.getLocationId()),
                likeOrReturnNull("description", searchModel.getDescriptionLike())
        );
        return employeeRepository.findAll(
                Specifications.And.<EmployeeEntity>builder()
                        .specifications(specifications)
                        .build(),
                sortDirection == null || StringUtils.isEmpty(sortProperty)
                        ? PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, ID_PROPERTY_NAME)
                        : PageRequest.of(pageNumber, pageSize, sortDirection, sortProperty));
    }

    @Override
    public void deleteById(Long id) {
        EmployeeEntity employee = findById(id);
        FileEntity file = employee.getFile();
        employeeRepository.deleteById(id);
        if (file != null) {
            fileService.deleteById(file.getId());
        }
    }

    @Override
    public Page<EmployeeEntity> findAll(Specification<EmployeeEntity> specification, PageRequest request) {
        return employeeRepository.findAll(specification, request);
    }

    @Override
    public long countItemsByFilter(Specification<EmployeeEntity> specification) {
        return employeeRepository.count(specification);
    }
}
