from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import JugadorViewSet, EquipoViewSet

router = DefaultRouter()
router.register(r'jugadores', JugadorViewSet)
router.register(r'equipos', EquipoViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
