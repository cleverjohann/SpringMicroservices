package com.microservice.producto_categoria;
import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;
import com.microservice.producto_categoria.domain.entities.Producto;
import com.microservice.producto_categoria.domain.repositories.CategoriaRepository;
import com.microservice.producto_categoria.domain.repositories.ProductoRepository;
import com.microservice.producto_categoria.infraestructure.services.Productoservice;
import com.microservice.producto_categoria.mappers.ProductoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Clase de prueba para Productoservice.
 * Esta clase contiene pruebas unitarias para la clase Productoservice,
 * Probando varios escenarios incluyendo operaciones exitosas y casos de borde.
 */
class ProductoserviceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private Productoservice productoService;
    /**
     * Configura el entorno de pruebas antes de cada prueba.
     * Inicializa los mocks y los inyecta en la instancia de Productoservice.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Comprueba la creación correcta de un producto.
     * Asegura que el producto es creado y guardado correctamente.
     */
    @Test
    void createProductSuccessfully() {
        ProductoRequest request = new ProductoRequest();
        request.setCategoriaId(1L); // Ensure categoriaId is set
        Categoria categoria = new Categoria();
        Producto producto = new Producto();
        ProductoResponse response = new ProductoResponse();
        when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.of(categoria));
        when(productoMapper.toEntity(any(ProductoRequest.class), any(Categoria.class))).thenReturn(producto);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoMapper.toResponse(any(Producto.class))).thenReturn(response);

        ProductoResponse result = productoService.create(request);

        assertEquals(response, result);
        verify(categoriaRepository, times(1)).findById(any(Long.class));
        verify(productoRepository, times(1)).save(producto);
    }
    /**
     * Prueba la creación de un producto cuando no se encuentra la categoría.
     * Asegura que se lanza una RuntimeException.
     */
    @Test
    void createProductCategoryNotFound() {
        ProductoRequest request = new ProductoRequest();
        request.setCategoriaId(2L); // Ensure categoriaId is set
        when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.create(request));
        verify(categoriaRepository, times(1)).findById(any(Long.class));
    }
    /**
     * Comprueba la actualización correcta de un producto.
     * Asegura que el producto es actualizado y guardado correctamente.
     */
    @Test
    void updateProductSuccessfully() {
        Long id = 1L;
        ProductoRequest request = new ProductoRequest();
        request.setCategoriaId(1L); // Ensure categoriaId is set
        Producto producto = new Producto();
        Categoria categoria = new Categoria();
        ProductoResponse response = new ProductoResponse();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoMapper.toResponse(any(Producto.class))).thenReturn(response);

        ProductoResponse result = productoService.update(id, request);

        assertEquals(response, result);
        verify(productoRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).findById(any(Long.class));
        verify(productoRepository, times(1)).save(producto);
    }
    /**
     * Prueba la actualización de un producto cuando no se encuentra el producto.
     * Asegura que se lanza una RuntimeException.
     */
    @Test
    void updateProductNotFound() {
        Long id = 1L;
        ProductoRequest request = new ProductoRequest();
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.update(id, request));
        verify(productoRepository, times(1)).findById(id);
    }
    /**
     * Prueba la actualización de un producto cuando no se encuentra la categoría.
     * Asegura que se lanza una RuntimeException.
     */
    @Test
    void updateProductCategoryNotFound() {
        Long id = 1L;
        ProductoRequest request = new ProductoRequest();
        request.setCategoriaId(2L); // Ensure categoriaId is set
        Producto producto = new Producto();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(categoriaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.update(id, request));
        verify(productoRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).findById(any(Long.class));
    }
    /**
     * Comprueba la correcta recuperación de un producto por su ID.
     * Asegura que el producto es encontrado y devuelto correctamente.
     */

    @Test
    void findProductByIdSuccessfully() {
        Long id = 1L;
        Producto producto = new Producto();
        ProductoResponse response = new ProductoResponse();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(productoMapper.toResponse(any(Producto.class))).thenReturn(response);

        ProductoResponse result = productoService.findById(id);

        assertEquals(response, result);
        verify(productoRepository, times(1)).findById(id);
    }
    /**
     * Prueba la recuperación de un producto por su ID cuando no se encuentra el producto.
     * Asegura que se lanza una RuntimeException.
     */
    @Test
    void findProductByIdNotFound() {
        Long id = 1L;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.findById(id));
        verify(productoRepository, times(1)).findById(id);
    }
    /**
     * Comprueba la eliminación correcta de un producto por su ID.
     * Asegura que el producto es borrado correctamente.
     */
    @Test
    void deleteProductSuccessfully() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.delete(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
    /**
     * Comprueba la recuperación correcta de todos los productos.
     * Asegura que todos los productos son encontrados y devueltos correctamente.
     */
    @Test
    void findAllProductsSuccessfully() {
        List<Producto> productos = Collections.singletonList(new Producto());
        List<ProductoResponse> responses = Collections.singletonList(new ProductoResponse());
        when(productoRepository.findAll()).thenReturn(productos);
        when(productoMapper.toResponse(any(Producto.class))).thenReturn(responses.get(0));

        List<ProductoResponse> result = productoService.findAll();

        assertEquals(responses, result);
        verify(productoRepository, times(1)).findAll();
    }
}