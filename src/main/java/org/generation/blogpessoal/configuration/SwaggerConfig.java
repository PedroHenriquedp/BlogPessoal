package org.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Projeto Blog Pessoal")
					.description("Projeto Blog Pessoal - Generation Brasil")
					.version("v0.0.1")
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/"))
				.contact(new Contact()
					.name("Conteudo Generation")
					.url("https://github.com/conteudoGeneration")
					.email("conteudogeneration@gmail.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Github")
					.url("https://github.com/conteudoGeneration/"));
	}

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Tudo Certo!!!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!!!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto DELETADO!!!"));
				apiResponses.addApiResponse("400", createApiResponse("Falha Na Comunicação!!!"));
				apiResponses.addApiResponse("401", createApiResponse("Você Não Deveria Estar Aqui!!!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!!!"));
				apiResponses.addApiResponse("500", createApiResponse("Estamos Com Problemas!!!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}
	
}