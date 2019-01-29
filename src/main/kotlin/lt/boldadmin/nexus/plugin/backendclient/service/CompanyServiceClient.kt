package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CompanyService
import lt.boldadmin.nexus.api.type.entity.Company
import lt.boldadmin.nexus.plugin.backendclient.get
import lt.boldadmin.nexus.plugin.backendclient.postJson

class CompanyServiceClient: CompanyService {

    override fun save(company: Company) = postJson("/company/save", company)

    override fun existsByName(name: String) = get("/company/name/$name/exists", Boolean::class.java)

}