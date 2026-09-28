from manager.models import Manager


def authenticate_manager(number, password):
    return Manager.objects.filter(number=number, password=password).first()
