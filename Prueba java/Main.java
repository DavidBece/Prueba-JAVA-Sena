import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Libro {
    private String titulo;
    private String autor;
    private int añoPublicacion;

    public Libro(String titulo, String autor, int añoPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor + " (" + añoPublicacion + ")";
    }
}

class Usuario {
    private String nombre;
    private int id;

    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}

class Biblioteca {
    List<Libro> librosDisponibles;
    private List<Libro> librosPrestados;

    public Biblioteca() {
        librosDisponibles = new ArrayList<>();
        librosPrestados = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        librosDisponibles.add(libro);
    }

    public void prestarLibro(Libro libro, Usuario usuario) throws Exception {
        if (librosDisponibles.contains(libro)) {
            librosDisponibles.remove(libro);
            librosPrestados.add(libro);
            JOptionPane.showMessageDialog(null, "El libro '" + libro.getTitulo() + "' ha sido prestado a " + usuario.getNombre());
        } else {
            throw new Exception("El libro no está disponible para ser prestado.");
        }
    }

    public void devolverLibro(Libro libro) throws Exception {
        if (librosPrestados.contains(libro)) {
            librosPrestados.remove(libro);
            librosDisponibles.add(libro);
            JOptionPane.showMessageDialog(null, "El libro '" + libro.getTitulo() + "' ha sido devuelto.");
        } else {
            throw new Exception("El libro no fue prestado anteriormente.");
        }
    }

    public void mostrarCatalogo() {
        StringBuilder catalogo = new StringBuilder("Catálogo de la biblioteca:\n");
        for (Libro libro : librosDisponibles) {
            catalogo.append(libro).append("\n");
        }
        JOptionPane.showMessageDialog(null, catalogo.toString());
    }

    public void mostrarLibrosPrestados() {
        StringBuilder prestados = new StringBuilder("Libros prestados:\n");
        for (Libro libro : librosPrestados) {
            prestados.append(libro).append("\n");
        }
        JOptionPane.showMessageDialog(null, prestados.toString());
    }
}

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        JFrame frame = new JFrame("Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JButton agregarLibroButton = new JButton("Agregar nuevo libro");
        JButton prestarLibroButton = new JButton("Prestar libro");
        JButton devolverLibroButton = new JButton("Devolver libro");
        JButton mostrarCatalogoButton = new JButton("Mostrar catálogo completo");
        JButton mostrarPrestadosButton = new JButton("Mostrar libros prestados");
        JButton salirButton = new JButton("Salir");

        agregarLibroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para agregar un nuevo libro
                String titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
                if (titulo != null) {
                    String autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
                    if (autor != null) {
                        String año = JOptionPane.showInputDialog("Ingrese el año de publicación del libro:");
                        if (año != null) {
                            try {
                                int añoPublicacion = Integer.parseInt(año);
                                biblioteca.agregarLibro(new Libro(titulo, autor, añoPublicacion));
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Ingrese un año válido.");
                            }
                        }
                    }
                }
            }
        });

        prestarLibroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para prestar un libro
                biblioteca.mostrarCatalogo();
                String tituloPrestamo = JOptionPane.showInputDialog("Ingrese el título del libro que desea prestar:");
                if (tituloPrestamo != null) {
                    String nombreUsuarioPrestamo = JOptionPane.showInputDialog("Ingrese su nombre:");
                    if (nombreUsuarioPrestamo != null) {
                        String idUsuarioPrestamoStr = JOptionPane.showInputDialog("Ingrese su ID:");
                        if (idUsuarioPrestamoStr != null) {
                            try {
                                int idUsuarioPrestamo = Integer.parseInt(idUsuarioPrestamoStr);
                                Libro libroPrestamo = buscarLibroPorTitulo(biblioteca, tituloPrestamo);
                                if (libroPrestamo != null) {
                                    try {
                                        biblioteca.prestarLibro(libroPrestamo, new Usuario(nombreUsuarioPrestamo, idUsuarioPrestamo));
                                    } catch (Exception exception) {
                                        JOptionPane.showMessageDialog(null, exception.getMessage());
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "El libro no se encuentra en el catálogo.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Ingrese un ID válido.");
                            }
                        }
                    }
                }
            }
        });

        devolverLibroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para devolver un libro
                biblioteca.mostrarLibrosPrestados();
                String tituloDevolucion = JOptionPane.showInputDialog("Ingrese el título del libro que desea devolver:");
                if (tituloDevolucion != null) {
                    Libro libroDevolucion = buscarLibroPorTitulo(biblioteca, tituloDevolucion);
                    if (libroDevolucion != null) {
                        try {
                            biblioteca.devolverLibro(libroDevolucion);
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El libro no se encuentra en la lista de libros prestados.");
                    }
                }
            }
        });

        mostrarCatalogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para mostrar el catálogo completo
                biblioteca.mostrarCatalogo();
            }
        });

        mostrarPrestadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para mostrar libros prestados
                biblioteca.mostrarLibrosPrestados();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para salir del programa
                System.exit(0);
            }
        });

        frame.add(agregarLibroButton);
        frame.add(prestarLibroButton);
        frame.add(devolverLibroButton);
        frame.add(mostrarCatalogoButton);
        frame.add(mostrarPrestadosButton);
        frame.add(salirButton);

        frame.setVisible(true);
    }

    private static Libro buscarLibroPorTitulo(Biblioteca biblioteca, String titulo) {
        for (Libro libro : biblioteca.librosDisponibles) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }
}
