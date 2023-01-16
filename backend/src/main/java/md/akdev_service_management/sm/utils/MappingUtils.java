package md.akdev_service_management.sm.utils;

import org.hibernate.MappingException;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public <T extends DTO> ModelMapper updateMapping(ModelMapper mapper, Class<T> dto){
        try {
            Constructor<T> constructor = dto.getConstructor();
            T instance = constructor.newInstance();
            return instance.updateModelMapper(mapper, this);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new MappingException(dto.getName());
        }
    }

    public <T extends DTO> ModelMapper getMapper(Class<T> target){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection)
                || ((PersistentCollection) context.getSource()).wasInitialized());
        return updateMapping(modelMapper, target);
    }


    public <S ,T extends DTO> List<T> mapList(List<S> source, Class<T> target){
        ModelMapper modelMapper = getMapper(target);

        return source.stream().map(el -> modelMapper.map(el,target)).collect(Collectors.toList());
    }

    public <S,T extends DTO> T map(S source, Class<T> target){
        ModelMapper modelMapper = getMapper(target);
        return modelMapper.map(source,target);
    }
}
