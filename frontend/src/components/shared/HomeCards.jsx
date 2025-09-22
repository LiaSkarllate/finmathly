import ActionLink from './ActionLink';
import Card from './Card';

const cards = [
    {
        id: 'modalities',
        title: 'Modalities',
        description: 'Manage your modalities.',
        browse: '/modalities',
        create: '/modalities/create',
        browseLabel: 'Browse modalities',
        createLabel: 'Create a modality',
    }
];

const HomeCards = () => {
    return (
        <section className="py-4">
            <div className="container mx-auto px-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">
                    {cards.map(card => (
                        <Card key={card.id}>
                            <h2 className="text-2xl font-bold">{card.title}</h2>
                            <p className="mt-2 mb-4">{card.description}</p>

                            <div className="flex flex-wrap gap-2">
                                <ActionLink to={card.browse}>{card.browseLabel}</ActionLink>
                                <ActionLink to={card.create}>{card.createLabel}</ActionLink>
                            </div>
                        </Card>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default HomeCards;
