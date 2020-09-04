package ru.otus.homework.atm.deposit.Banknote;

public interface IBanknote {
    IBanknote B5000 = new IBanknote() {
        @Override
        public int getNominal() {
            return 5000;
        }
    };
    IBanknote B2000 = new IBanknote() {
        @Override
        public int getNominal() {
            return 2000;
        }
    };
    IBanknote B1000 = new IBanknote() {
        @Override
        public int getNominal() {
            return 1000;
        }
    };
    IBanknote B500 = new IBanknote() {
        @Override
        public int getNominal() {
            return 500;
        }
    };
    IBanknote B200 = new IBanknote() {
        @Override
        public int getNominal() {
            return 200;
        }
    };
    IBanknote B100 = new IBanknote() {
        @Override
        public int getNominal() {
            return 100;
        }
    };

    int getNominal();
}
