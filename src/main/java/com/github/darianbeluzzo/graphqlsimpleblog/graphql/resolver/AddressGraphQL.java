package com.github.darianbeluzzo.graphqlsimpleblog.graphql.resolver;

import com.github.darianbeluzzo.graphqlsimpleblog.domain.Address;
import com.github.darianbeluzzo.graphqlsimpleblog.graphql.model.AddressInput;
import com.github.darianbeluzzo.graphqlsimpleblog.repository.AddressRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressGraphQL implements GraphQLQueryResolver, GraphQLMutationResolver {

    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper mapper;

    public AddressGraphQL(final AddressRepository addressRepository) {
	this.addressRepository = addressRepository;
    }

    public Address createAddress(final AddressInput addressInput) {
	final Address address = mapper.map(addressInput, Address.class);
	return this.addressRepository.save(address);
    }

    public List<Address> listAddresses() {
	return this.addressRepository.findAll();
    }
}
