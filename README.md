
# ⚽ Evaluador Táctico de Jugadores

**Transforma la forma en que evalúas a tus futbolistas.**  
Una app creada para entrenadores, analistas y formadores que quieren ir más allá de la intuición.

---

## 🧠 ¿Qué es esta app?

_Evaluador Táctico de Jugadores_ es una aplicación móvil que te permite analizar a tus jugadores en profundidad, utilizando 36 acciones tácticas fundamentales del fútbol moderno:  
18 ofensivas + 18 defensivas, valoradas individualmente del 1 al 5.

Una herramienta ideal para academias, cuerpos técnicos y scouting profesional.

---

## ✨ Características principales

✅ **Evaluación detallada:**  
Cada jugador puede ser valorado en 36 aspectos tácticos clave.

✅ **Análisis objetivo:**  
Asigna una puntuación del 1 al 5 para cada acción. Calculamos automáticamente su media táctica.

✅ **Gestión de equipos y jugadores:**  
Organiza tus futbolistas por equipo y lleva un seguimiento estructurado.

✅ **Modificable y dinámico:**  
Actualiza las valoraciones cuando quieras. Los jugadores evolucionan, y tus análisis también.

✅ **Todo sincronizado:**  
La información se guarda de forma segura en una base de datos online.

---

## 👥 ¿A quién va dirigida?

- Entrenadores de fútbol base y profesional  
- Analistas tácticos  
- Scouts y ojeadores  
- Coordinadores deportivos  
- Academias de formación futbolística  

---

## 📲 ¿Cómo se usa?

1. **Crea o selecciona un jugador**
2. **Evalúa cada acción táctica** (con solo un toque)
3. **Guarda y compara los resultados**
4. **Actualiza tus valoraciones cuando lo necesites**

---

## 🚀 Ventajas competitivas

- Basada en fundamentos tácticos reales
- Interfaz intuitiva y ágil
- Pensada por y para gente del fútbol
- Totalmente adaptable a diferentes niveles (base, semiprofesional, profesional)

---

## 🏗️ En desarrollo...

Esta app está en continuo crecimiento. Próximas funciones:

- Comparador entre jugadores
- Historial de evolución táctica
- Gráficas interactivas de rendimiento
- Exportación en PDF o Excel

---

## 🛠️ Tecnologías empleadas

### Frontend (App Android)
- **Java**
- **Android Studio**
- **MVVM**
- Librerías:
  - Volley
  - LiveData / ViewModel
  - RecyclerView

### Backend (Django REST)
- **Python 3.8**
- **Django Rest Framework**
- **SQLite** como base de datos
- Comunicación vía API REST en JSON

---

## 🧪 Pasos para probar la aplicación

### Backend (Django)
1. Clona el repositorio
2. Instala las dependencias:
   ```bash
   pip install -r requirements.txt
   ```
3. Ejecuta el servidor:
   ```bash
   python manage.py runserver
   ```

> ⚠️ Asegúrate de que el backend se ejecuta en `http://10.0.2.2:8000` para ser accesible desde el emulador Android.

### Frontend (Android)
1. Abre el proyecto en Android Studio
2. Conecta un emulador o dispositivo físico (Android 7.0+)
3. Compila y lanza la app
4. Prueba la creación de equipos, jugadores y evaluaciones tácticas

---

## 💬 Contacto

Diego Blanco Mosqueira  
blancomosqueira@gmail.com
