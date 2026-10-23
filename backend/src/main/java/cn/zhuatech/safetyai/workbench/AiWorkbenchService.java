/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.safetyai.workbench;

import static cn.zhuatech.safetyai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "asOf", "roles", "workers");
    var asOf = date(text(r, "asOf", 10));
    Map<String, List<String>> roles = new HashMap<>();
    for (var role : rows(r, "roles", 1, 50)) {
      keys(role, "id", "required");
      String id = id(role, "id");
      var required = strings(role, "required", 1, 50);
      if (new HashSet<>(required).size() != required.size()
          || roles.putIfAbsent(id, required) != null)
        throw new IllegalArgumentException("岗位或证书类型重复");
    }
    Set<String> ids = new HashSet<>();
    List<Map<String, Object>> out = new ArrayList<>();
    int qualified = 0;
    for (var worker : rows(r, "workers", 1, 100)) {
      keys(worker, "id", "role", "certificates");
      String id = id(worker, "id");
      unique(ids, id);
      String role = id(worker, "role");
      if (!roles.containsKey(role)) throw new IllegalArgumentException("未知岗位");
      Map<String, Map<String, Object>> certs = new HashMap<>();
      for (var c : rows(worker, "certificates", 0, 50)) {
        keys(c, "type", "validFrom", "validTo");
        String type = text(c, "type", 100);
        var from = date(text(c, "validFrom", 10));
        var to = date(text(c, "validTo", 10));
        if (from.isAfter(to) || certs.putIfAbsent(type, c) != null)
          throw new IllegalArgumentException("资质有效期或类型错误");
      }
      boolean ready = true;
      List<Map<String, Object>> checks = new ArrayList<>();
      for (String type : roles.get(role)) {
        var c = certs.get(type);
        String state =
            c == null
                ? "MISSING"
                : asOf.isBefore(date(c.get("validFrom").toString()))
                    ? "NOT_YET_VALID"
                    : asOf.isAfter(date(c.get("validTo").toString())) ? "EXPIRED" : "VALID";
        if (!state.equals("VALID")) ready = false;
        checks.add(Map.of("type", type, "status", state));
      }
      if (ready) qualified++;
      out.add(Map.of("id", id, "role", role, "qualificationsCurrent", ready, "checks", checks));
    }
    return Map.of("qualifiedCount", qualified, "workers", out, "automaticPermit", false);
  }
}
