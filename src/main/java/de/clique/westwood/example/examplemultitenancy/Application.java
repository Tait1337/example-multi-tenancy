package de.clique.westwood.example.examplemultitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Software multitenancy is a software architecture in which a single instance of software runs on a server and serves multiple tenants.
 * A tenant is a group of users who share a common access with specific privileges to the software instance.
 * The distinction between the customers is achieved during application design, thus customers do not share or see each other's data.
 * + Multitenancy allows for cost savings over and above the basic economies of scale achievable from consolidating IT resources into a single operation.
 * + Instead of collecting data from multiple data sources, all data for all customers is stored in a single database. Thus, running queries across customers, mining data, and looking for trends is much simpler.
 * + Multitenancy simplifies the release management process.
 * - Because of the additional customization complexity and the need to maintain per-tenant metadata, multitenant applications require a larger development effort.
 * @see https://en.wikipedia.org/wiki/Multitenancy
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
