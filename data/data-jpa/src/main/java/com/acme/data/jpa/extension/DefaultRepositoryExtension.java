package com.acme.data.jpa.extension;

import org.springframework.data.repository.core.RepositoryMethodContext;
import org.springframework.data.repository.core.support.RepositoryMetadataAccess;

class DefaultRepositoryExtension<T> implements RepositoryExtension<T>, RepositoryMetadataAccess {

	@Override
	public String extensionMethod(String arg) {

		return "RepositoryExtension says %s to %s".formatted(arg,
				RepositoryMethodContext.getContext().getMetadata().getDomainType().getSimpleName());
	}

}
