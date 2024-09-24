package com.microservice.producto;


import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.domain.repositories.CategoriaRepository;
import com.microservice.categoria.util.CategoriaNotFoundException;
import com.microservice.producto.domain.entities.ProductoEntity;
import com.microservice.producto.domain.repositories.ProductoRepository;
import com.microservice.producto.infraestructure.services.ProductoService;
import com.microservice.producto.util.ProductoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    //Guardar producto
    @Test
    public void save_ReturnsSavedProducto_WhenCategoriaExists() {
        ProductoEntity producto = new ProductoEntity();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1L);
        producto.setCategoria(categoria);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(producto)).thenReturn(producto);

        ProductoEntity result = productoService.save(producto);

        assertNotNull(result);
        assertEquals(categoria, result.getCategoria());
    }

    @Test
    public void save_ThrowsException_WhenCategoriaDoesNotExist() {
        ProductoEntity producto = new ProductoEntity();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(999L);
        producto.setCategoria(categoria);

        when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> productoService.save(producto));
    }

    //Actualizar producto
    @Test
    public void update_UpdatesAndReturnsProducto_WhenIdAndCategoriaExist() {
        Integer id = 1;
        ProductoEntity producto = new ProductoEntity();
        producto.setNombre("Updated Name");
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1L);
        producto.setCategoria(categoria);
        ProductoEntity existingProducto = new ProductoEntity();
        existingProducto.setId(Long.valueOf(id));

        when(productoRepository.findById(id)).thenReturn(Optional.of(existingProducto));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(existingProducto)).thenReturn(existingProducto);

        ProductoEntity result = productoService.update(id, producto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNombre());
    }

    @Test
    public void update_ThrowsException_WhenProductoIdDoesNotExist() {
        Integer id = 999;
        ProductoEntity producto = new ProductoEntity();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(1L);
        producto.setCategoria(categoria);

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.update(id, producto));
    }

    @Test
    public void update_ThrowsException_WhenCategoriaDoesNotExist() {
        Integer id = 1;
        ProductoEntity producto = new ProductoEntity();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(999L);
        producto.setCategoria(categoria);
        ProductoEntity existingProducto = new ProductoEntity();
        existingProducto.setId(Long.valueOf(id));

        when(productoRepository.findById(id)).thenReturn(Optional.of(existingProducto));
        when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> productoService.update(id, producto));
    }

    //Borrar producto
    @Test
    public void delete_ReturnsTrue_WhenProductoIdExists() {
        Integer id = 1;
        ProductoEntity producto = new ProductoEntity();
        producto.setId(Long.valueOf(id));

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        boolean result = productoService.delete(id);

        assertTrue(result);
    }

    @Test
    public void delete_ThrowsException_WhenProductoIdDoesNotExist() {
        Integer id = 999;

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.delete(id));
    }

    //Buscar por ID Producto
    @Test
    public void findById_ReturnsProducto_WhenIdExists() {
        Long id = 1L;
        ProductoEntity producto = new ProductoEntity();
        producto.setId(id);

        when(productoRepository.findById(Math.toIntExact(id))).thenReturn(Optional.of(producto));

        ProductoEntity result = productoService.findById(Math.toIntExact(id));

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void findById_ReturnsNull_WhenIdDoesNotExist() {
        Integer id = 999;

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        ProductoEntity result = productoService.findById(id);

        assertNull(result);
    }
    //Listar todos los productos

    @Test
    public void findAll_ReturnsListOfProductos() {
        List<ProductoEntity> productos = Arrays.asList(new ProductoEntity(), new ProductoEntity());

        when(productoRepository.findAll()).thenReturn(productos);

        List<ProductoEntity> result = productoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}