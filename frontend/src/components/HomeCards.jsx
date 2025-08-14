import ActionLink from './ActionLink';
import Card from './Card';

const cards = [
    {
        id: 'assets',
        title: 'Assets',
        desc: 'Manage your assets.',
        browse: '/assets',
        add: '/assets/new',
        browseLabel: 'Browse assets',
        addLabel: 'Add an asset',
    },
    {
        id: 'modalities',
        title: 'Modalities',
        desc: 'Manage your modalities.',
        browse: '/modalities',
        add: '/modalities/new',
        browseLabel: 'Browse modalities',
        addLabel: 'Add a modality',
    },
    {
        id: 'flows',
        title: 'Flows',
        desc: 'Manage your asset flows.',
        browse: '/flows',
        add: '/flows/new',
        browseLabel: 'Browse flows',
        addLabel: 'Add a flow to an asset',
    },
    {
        id: 'market-indexes',
        title: 'Market indexes',
        desc: 'Manage your market indexes.',
        browse: '/market-indexes',
        add: '/market-indexes/new',
        browseLabel: 'Browse market indexes',
        addLabel: 'Add a market index',
    },
];

const HomeCards = () => {
    return (
        <section className="py-4">
            <div className="container mx-auto px-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">
                    {cards.map(card => (
                        <Card key={card.id}>
                            <h2 className="text-2xl font-bold">{card.title}</h2>
                            <p className="mt-2 mb-4">{card.desc}</p>

                            <div className="flex flex-wrap gap-2">
                                <ActionLink to={card.browse}>{card.browseLabel}</ActionLink>
                                <ActionLink to={card.add}>{card.addLabel}</ActionLink>
                            </div>
                        </Card>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default HomeCards;
