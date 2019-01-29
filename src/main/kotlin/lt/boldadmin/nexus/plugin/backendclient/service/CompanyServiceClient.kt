package lt.boldadmin.nexus.plugin.backendclient.service

import lt.boldadmin.nexus.api.service.CompanyService
import lt.boldadmin.nexus.api.type.entity.Company
import lt.boldadmin.nexus.plugin.backendclient.httpclient.BackendHttpClient

class CompanyServiceClient(private val httpClient: BackendHttpClient = BackendHttpClient()): CompanyService {

    override fun save(company: Company) = httpClient.postJson("/company/save", company)

    override fun existsByName(name: String) = httpClient.get("/company/name/$name/exists", Boolean::class.java)

}