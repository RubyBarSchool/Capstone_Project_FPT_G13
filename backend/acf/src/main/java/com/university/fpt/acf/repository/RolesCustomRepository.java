package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.RolesForm;
import com.university.fpt.acf.vo.GetAllRoleVO;

import java.util.List;

public interface RolesCustomRepository {
    List<GetAllRoleVO> getRoles(RolesForm rolesForm);
    int totalGetRoles(RolesForm rolesForm);
}
