package dev.patika.hrmsManager.core.config.modelMapper;

import org.modelmapper.ModelMapper;
public interface IModelMapperService {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
